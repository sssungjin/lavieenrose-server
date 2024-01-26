package org.dongguk.lavieenrosehotel.service

import org.dongguk.lavieenrosehotel.domain.*
import org.dongguk.lavieenrosehotel.dto.common.PageInfo
import org.dongguk.lavieenrosehotel.dto.request.ReservationAmenityDto
import org.dongguk.lavieenrosehotel.dto.request.ReservationRoomDto
import org.dongguk.lavieenrosehotel.dto.request.ReservationStatusDto
import org.dongguk.lavieenrosehotel.dto.request.ReservationTransportationDto
import org.dongguk.lavieenrosehotel.dto.response.*
import org.dongguk.lavieenrosehotel.dto.type.EAmenityType
import org.dongguk.lavieenrosehotel.dto.type.EVisibleStatus
import org.dongguk.lavieenrosehotel.event.PaymentVerifyEvent
import org.dongguk.lavieenrosehotel.event.ReservationCancelEvent
import org.dongguk.lavieenrosehotel.event.ReservationEvent
import org.dongguk.lavieenrosehotel.exception.CommonException
import org.dongguk.lavieenrosehotel.exception.ErrorCode
import org.dongguk.lavieenrosehotel.repository.*
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.Period

@Service
class ReservationService(
    private val userRepository: UserRepository,

    private val reservationRepository: ReservationRepository,
    private val reservationRoomRepository: ReservationRoomRepository,
    private val reservationAmenityRepository: ReservationAmenityRepository,
    private val reservationTransportationRepository: ReservationTransportationRepository,

    private val categoryRepository: CategoryRepository,
    private val amenityRepository: AmenityRepository,
    private val breakfastOrderRepository: BreakfastOrderRepository,

    private val publisher: ApplicationEventPublisher,
) {
    fun calculateReservationRoomPrice(
        totalCnt: Int,
        categoryId: Long,
        startDate: LocalDate,
        endDate: LocalDate,
    ): ReservationRoomPriceDto {
        val category: Category = categoryRepository.findCategoryByIdAndStatus(categoryId, EVisibleStatus.VISIBLE)
            .orElseThrow { CommonException(ErrorCode.NOT_FOUND) }

        val totalPrice = (Period.between(startDate, endDate).days).let {days ->
            val price = LocalDate.now().let {now ->
                if (now.monthValue in listOf(1, 2, 7, 8, 12)) {
                    category.peekPrice
                } else {
                    category.defaultPrice
                }
            }

            val defaultCnt = (totalCnt - category.standardCapacity).let {cnt ->
                if (cnt < 0) {
                    totalCnt
                } else {
                    category.standardCapacity
                }
            }

            val additionCnt = (totalCnt - category.standardCapacity).let {cnt ->
                if (cnt < 0) {
                    0
                } else {
                    cnt
                }
            }

            if (days == 0) {
                price * defaultCnt + category.additionPrice * additionCnt
            } else {
                price * days * defaultCnt + category.additionPrice * days * additionCnt
            }
        }

        return ReservationRoomPriceDto.fromValue(totalPrice)
    }

    fun calculateReservationAmenityPrice(
        totalCnt: Int,
        amenityType: EAmenityType,
    ): ReservationAmenityPriceDto {
        val amenity: Amenity = amenityRepository.findByAmenityType(amenityType).orElseThrow {
            CommonException(ErrorCode.NOT_FOUND_AMENITY)
        }

        val totalPrice = LocalDate.now().let { date ->
            val price = if (date.monthValue in listOf(1, 2, 7, 8, 12)) {
                (amenity.allDayPrice * 1.2).toInt()
            } else {
                amenity.allDayPrice
            }

            price * totalCnt
        }

        return ReservationAmenityPriceDto.fromEntity(totalPrice)
    }
    fun createReservationTransportations(userId: Long, requestBody: ReservationTransportationDto): Any {
        val user: User = userRepository.findById(userId)
            .orElseThrow { CommonException(ErrorCode.NOT_FOUND_USER) }

        val reservation = ReservationTransportation.fromJson(requestBody, user)

        reservationTransportationRepository.save(reservation)

        publisher.publishEvent(
            ReservationEvent(
                impUid = requestBody.impUid,
                isRefund = false,
                userId = userId,
                reservationId = reservation.id!!,
                price = requestBody.paymentPrice,
                method = requestBody.method,
            )
        )

        return ReservationTransportationListDto.fromEntity(reservation, requestBody)
    }


    fun readReservationTransportations(userId: Long, page: Int, size: Int): Map<String, Any> {
        val user: User = userRepository.findById(userId)
            .orElseThrow { CommonException(ErrorCode.NOT_FOUND_USER) }

        val pages: Page<ReservationTransportation> =
            Pageable.ofSize(size).withPage(page - 1).let {
                reservationTransportationRepository.findAllBy_user(user, it)
            }

        return mapOf(
            "page_info" to PageInfo.of(pages),
            "reservations" to pages.content.map {
                ReservationTransportationListDto.fromEntity(it)
            }
        )
    }

    @Transactional
    fun deleteReservationTransportation(userId: Long, reservationTransportationId: Long) {
        val user: User = userRepository.findById(userId)
            .orElseThrow { CommonException(ErrorCode.NOT_FOUND_USER) }

        val reservation: ReservationTransportation =
            reservationTransportationRepository.findById(reservationTransportationId)
                .orElseThrow { CommonException(ErrorCode.NOT_FOUND) }

        if (reservation.user != user) {
            throw CommonException(ErrorCode.BAD_REQUEST)
        }

        reservationTransportationRepository.delete(reservation)

        publisher.publishEvent(
            ReservationCancelEvent(
                reservationId = reservation.id!!,
            )
        )
    }

    @Transactional
    fun createReservationRooms(userId: Long, requestBody: ReservationRoomDto): Any {
        val user: User = userRepository.findById(userId)
            .orElseThrow { CommonException(ErrorCode.NOT_FOUND_USER) }

        val category = categoryRepository.findCategoryByIdAndStatus(requestBody.categoryId, EVisibleStatus.VISIBLE)
            .orElseThrow { CommonException(ErrorCode.RESERVATION_FULL) }

        val selectedRoom = category.rooms.stream().filter {
            it.reservationRooms.stream().noneMatch { reservationRoom ->
                !(reservationRoom.startDate.isAfter(requestBody.endDate) || reservationRoom.endDate.isBefore(requestBody.startDate))
            }
        }.findFirst().orElseThrow {
            CommonException(ErrorCode.RESERVATION_FULL)
        }

        // 조식 여부 검사
        val reservationPeriod = Period.between(requestBody.startDate, requestBody.endDate).days
        if (requestBody.breakfastOrders.size != reservationPeriod) {
            throw CommonException(ErrorCode.INVALID_BREAKFAST_ORDER)
        }

        // 예약 저장
        val reservation = reservationRoomRepository.saveAndFlush(
            ReservationRoom.fromJson(
                requestBody,
                user,
                selectedRoom,
                category)
        )

        // 조식 여부 저장
        val breakfastOrders = requestBody.breakfastOrders.mapIndexed { index, isEating ->
            val applyDate = requestBody.startDate.plusDays(index.toLong() + 1)
            BreakfastOrder.createOrder(applyDate, isEating, reservation)
        }
        breakfastOrderRepository.saveAll(breakfastOrders)


        publisher.publishEvent(
            ReservationEvent(
                impUid = requestBody.impUid,
                isRefund = false,
                userId = userId,
                reservationId = reservation.id!!,
                price = requestBody.paymentPrice,
                method = requestBody.method,
            )
        )

        return ReservationRoomListDto.fromEntity(reservation, requestBody)
    }

    fun readReservationRooms(userId: Long, page: Int, size: Int): Map<String, Any> {
        val user: User = userRepository.findById(userId)
            .orElseThrow { CommonException(ErrorCode.NOT_FOUND_USER) }

        val pages: Page<ReservationRoom> = PageRequest.of(page - 1, size).let {
            reservationRoomRepository.findAllBy_user(user, it)
        }

        return mapOf(
            "page_info" to PageInfo.of(pages),
            "reservations" to pages.content.map {
                ReservationRoomListDto.fromEntity(it)
            }
        )
    }

    fun readReservationRoomDetail(userId: Long, reservationRoomId: Long): ReservationRoomDetailDto {
        val user: User = userRepository.findById(userId)
            .orElseThrow { CommonException(ErrorCode.NOT_FOUND_USER) }

        val reservationRoom: ReservationRoom = reservationRoomRepository.findById(reservationRoomId)
            .orElseThrow { CommonException(ErrorCode.NOT_FOUND) }

        if (reservationRoom.user != user) {
            throw CommonException(ErrorCode.BAD_REQUEST)
        }

        return ReservationRoomDetailDto.fromEntity(reservationRoom)
    }

    @Transactional
    fun deleteReservationRoom(userId: Long, reservationRoomId: Long) {
        val user: User = userRepository.findById(userId)
            .orElseThrow { CommonException(ErrorCode.NOT_FOUND_USER) }

        val reservationRoom: ReservationRoom = reservationRoomRepository.findById(reservationRoomId)
            .orElseThrow { CommonException(ErrorCode.NOT_FOUND) }

        if (reservationRoom.user != user) {
            throw CommonException(ErrorCode.BAD_REQUEST)
        }

        publisher.publishEvent(
            ReservationCancelEvent(
                reservationId = reservationRoom.id!!,
            )
        )

        reservationRoomRepository.delete(reservationRoom)
    }

    fun createReservationAmenities(userId: Long, requestBody: ReservationAmenityDto): Any {
        val user: User = userRepository.findById(userId)
            .orElseThrow { CommonException(ErrorCode.NOT_FOUND_USER) }

        val amenity: Amenity = amenityRepository.findByAmenityType(requestBody.amenityType)
            .orElseThrow { CommonException(ErrorCode.NOT_FOUND_AMENITY) }

        val reservation = reservationRepository.save(
            ReservationAmenity.fromJson(requestBody, user, amenity)
        )

        publisher.publishEvent(
            ReservationEvent(
                impUid = requestBody.impUid,
                isRefund = false,
                userId = userId,
                reservationId = reservation.id!!,
                price = requestBody.paymentPrice,
                method = requestBody.method,
            )
        )

        return ReservationAmenityListDto.fromEntity(reservation)
    }

    fun readReservationAmenities(userId: Long, page: Int, size: Int): Map<String, Any> {
        val user: User = userRepository.findById(userId)
            .orElseThrow { CommonException(ErrorCode.NOT_FOUND_USER) }

        val pages: Page<ReservationAmenity> = PageRequest.of(page - 1, size).let {
            reservationAmenityRepository.findAllBy_user(user, it)
        }

        return mapOf(
            "page_info" to PageInfo.of(pages),
            "reservations" to pages.content.map {
                ReservationAmenityListDto.fromEntity(it)
            }
        )
    }

    fun readReservationAmenityDetail(userId: Long, reservationAmenityId: Long): ReservationAmenityDetailDto {
        val user: User = userRepository.findById(userId)
            .orElseThrow { CommonException(ErrorCode.NOT_FOUND_USER) }

        val reservationAmenity: ReservationAmenity = reservationAmenityRepository.findById(reservationAmenityId)
            .orElseThrow { CommonException(ErrorCode.NOT_FOUND) }

        if (reservationAmenity.user != user) {
            throw CommonException(ErrorCode.BAD_REQUEST)
        }

        return ReservationAmenityDetailDto.fromEntity(reservationAmenity)
    }

    fun deleteReservationAmenity(userId: Long, reservationAmenityId: Long) {
        val user: User = userRepository.findById(userId)
            .orElseThrow { CommonException(ErrorCode.NOT_FOUND_USER) }

        val reservationAmenity: ReservationAmenity = reservationAmenityRepository.findById(reservationAmenityId)
            .orElseThrow { CommonException(ErrorCode.NOT_FOUND) }

        if (reservationAmenity.user != user) {
            throw CommonException(ErrorCode.BAD_REQUEST)
        }

        publisher.publishEvent(
            ReservationCancelEvent(
                reservationId = reservationAmenity.id!!,
            )
        )

        reservationAmenityRepository.delete(reservationAmenity)
    }

    @Transactional
    fun updateReservationRoomStatus(reservationRoomId: Long, requestBody: ReservationStatusDto) {
        val reservationRoom = reservationRoomRepository.findById(reservationRoomId)
            .orElseThrow { CommonException(ErrorCode.NOT_FOUND) }

        reservationRoom.updateStatus(requestBody.status)
    }

    @Transactional
    fun updateReservationAmenityStatus(reservationAmenityId: Long, requestBody: ReservationStatusDto) {
        val reservationAmenity = reservationAmenityRepository.findById(reservationAmenityId)
            .orElseThrow { CommonException(ErrorCode.NOT_FOUND) }

        reservationAmenity.updateStatus(requestBody.status)
    }


    // Event
    @Transactional
    fun updateStatus(event: PaymentVerifyEvent) {
        val reservation: Reservation = reservationRepository.findById(event.reservationId)
            .orElseThrow { CommonException(ErrorCode.NOT_FOUND_ENTITY_AMONG_PROCESSING_EVENT)
        }

        reservation.updateStatus(event.status)
    }
}
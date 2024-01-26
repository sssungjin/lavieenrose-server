package org.dongguk.lavieenrosehotel.controller

import jakarta.validation.Valid
import org.apache.coyote.Response
import org.dongguk.lavieenrosehotel.dto.common.ResponseDto
import org.dongguk.lavieenrosehotel.dto.request.*
import org.dongguk.lavieenrosehotel.dto.response.TransportationListDto
import org.dongguk.lavieenrosehotel.dto.type.EAmenityType
import org.dongguk.lavieenrosehotel.dto.type.ETransportationType
import org.dongguk.lavieenrosehotel.exception.CommonException
import org.dongguk.lavieenrosehotel.exception.ErrorCode
import org.dongguk.lavieenrosehotel.service.ReservationService
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.MissingRequestValueException
import java.time.LocalDate

@RestController
class ReservationController(
    private val reservationService: ReservationService,
) {
    /* Common API */
    @GetMapping("/reservation-room-price")
    fun calculateReservationRoomPrice(
        @RequestParam("totalCnt") totalCnt: Int,
        @RequestParam("categoryId") categoryId: Long,
        @RequestParam("startDate") startDate: LocalDate,
        @RequestParam("endDate") endDate: LocalDate,
    ): ResponseDto<*> {
        return if (totalCnt < 1 || startDate.isAfter(endDate)) {
            throw CommonException(ErrorCode.INVALID_ARGUMENT)
        } else {
            ResponseDto.ok(reservationService.calculateReservationRoomPrice(totalCnt, categoryId, startDate, endDate))
        }
    }

    @GetMapping("/reservation-amenity-price")
    fun calculateReservationAmenityPrice(
        @RequestParam("totalCnt") totalCnt: Int,
        @RequestParam("amenityType") amenityType: EAmenityType,
    ) = ResponseDto.ok(reservationService.calculateReservationAmenityPrice(totalCnt, amenityType))

    /* User API */
    @PostMapping("/reservation-transportations")
    fun createReservationTransportation(
        @RequestBody @Valid requestBody: ReservationTransportationDto,
    ): ResponseDto<*> {
        val userId: Long = 2
        return ResponseDto.created(reservationService.createReservationTransportations(userId, requestBody))
    }

    @GetMapping("/reservation-transportations")
    fun readReservationTransportations(
        @RequestParam("page", defaultValue = "1") page: Int,
        @RequestParam("size", defaultValue = "10") size: Int,
    ): ResponseDto<*> {
        val userId: Long = 2
        if (page < 1 || size < 1) {
            throw org.springframework.web.bind.MissingRequestValueException("page or size")
        }
        return ResponseDto.ok(reservationService.readReservationTransportations(userId, page, size))
    }

    @DeleteMapping("/reservation-transportations/{reservationTransportationId}")
    fun deleteReservationTransportation(
        @PathVariable("reservationTransportationId") reservationTransportationId: Long,
    ): ResponseDto<*> {
        val userId: Long = 2
        reservationService.deleteReservationTransportation(userId, reservationTransportationId)

        return ResponseDto.ok(null)
    }

    @PostMapping("/reservation-rooms")
    fun createReservationRoom(
        @RequestBody @Valid requestBody: ReservationRoomDto,
    ): ResponseDto<*> {
        val userId: Long = 2
        return ResponseDto.created(reservationService.createReservationRooms(userId, requestBody))
    }

    @GetMapping("/reservation-rooms")
    fun readReservationRooms(
        @RequestParam("page", defaultValue = "1") page: Int,
        @RequestParam("size", defaultValue = "10") size: Int,
    ): ResponseDto<*> {
        val userId: Long = 2
        if (page < 1 || size < 1) {
            throw org.springframework.web.bind.MissingRequestValueException("page or size")
        }
        return ResponseDto.ok(reservationService.readReservationRooms(userId, page, size))
    }

    @GetMapping("/reservation-rooms/{reservationRoomId}")
    fun readReservationRoomDetail(
        @PathVariable("reservationRoomId") reservationRoomId: Long,
    ): ResponseDto<*> {
        val userId: Long = 2
        return ResponseDto.ok(reservationService.readReservationRoomDetail(userId, reservationRoomId))
    }

    @DeleteMapping("/reservation-rooms/{reservationRoomId}")
    fun deleteReservationRoom(
        @PathVariable("reservationRoomId") reservationRoomId: Long,
    ): ResponseDto<*> {
        val userId: Long = 2
        reservationService.deleteReservationRoom(userId, reservationRoomId)

        return ResponseDto.ok(null)
    }

    @PostMapping("/reservation-amenities")
    fun createReservationAmenity(
        @RequestBody @Valid requestBody: ReservationAmenityDto,
    ): ResponseDto<*> {
        val userId: Long = 2
        return ResponseDto.created(reservationService.createReservationAmenities(userId, requestBody))
    }

    @GetMapping("/reservation-amenities")
    fun readReservationAmenities(
        @RequestParam("page", defaultValue = "1") page: Int,
        @RequestParam("size", defaultValue = "10") size: Int,
    ): ResponseDto<*> {
        val userId: Long = 2
        if (page < 1 || size < 1) {
            throw org.springframework.web.bind.MissingRequestValueException("page or size")
        }
        return ResponseDto.ok(reservationService.readReservationAmenities(userId, page, size))
    }

    @GetMapping("/reservation-amenities/{reservationAmenityId}")
    fun readReservationAmenityDetail(
        @PathVariable("reservationAmenityId") reservationAmenityId: Long,
    ): ResponseDto<*> {
        val userId: Long = 2
        return ResponseDto.ok(reservationService.readReservationAmenityDetail(userId, reservationAmenityId))
    }

    @DeleteMapping("/reservation-amenities/{reservationAmenityId}")
    fun deleteReservationAmenity(
        @PathVariable("reservationAmenityId") reservationAmenityId: Long,
    ): ResponseDto<*> {
        val userId: Long = 2
        reservationService.deleteReservationAmenity(userId, reservationAmenityId)

        return ResponseDto.ok(null)
    }

    /* Admin API */
    @PostMapping("/admin/reservation-amenities")
    fun createReservationAmenityByAdmin(
        @RequestBody @Valid requestBody: ReservationAmenityDto,
    ): ResponseDto<*> {
        val requestId: Long = 2
        return ResponseDto.created(reservationService.createReservationAmenities(requestId, requestBody))
    }

    @GetMapping("/admin/reservation-amenities")
    fun readReservationAmenitiesByAdmin(
        @RequestParam("page") page: Int,
        @RequestParam("size") size: Int,
    ): ResponseDto<*> {
        if (page < 1 || size < 1) {
            throw org.springframework.web.bind.MissingRequestValueException("page or size")
        }

        val requestId: Long = 2
        return ResponseDto.ok(reservationService.readReservationAmenities(requestId, page, size))
    }

    @GetMapping("/admin/reservation-amenities/{reservationAmenityId}")
    fun readReservationAmenityDetailByAdmin(
        @PathVariable("reservationAmenityId") reservationAmenityId: Long,
    ): ResponseDto<*> {
        val requestId: Long = 2
        return ResponseDto.ok(reservationService.readReservationAmenityDetail(requestId, reservationAmenityId))
    }

    @DeleteMapping("/admin/reservation-amenities/{reservationAmenityId}")
    fun deleteReservationAmenityByAdmin(
        @PathVariable("reservationAmenityId") reservationAmenityId: Long,
    ): ResponseDto<*> {
        val requestId: Long = 2
        return ResponseDto.ok(reservationService.deleteReservationAmenity(requestId, reservationAmenityId))
    }

    @PostMapping("/admin/reservation-rooms")
    fun createReservationRoomByAdmin(
        @RequestBody @Valid requestBody: ReservationRoomDto,
    ): ResponseDto<*> {
        val requestId: Long = 2
        return ResponseDto.created(reservationService.createReservationRooms(requestId, requestBody))
    }

    @GetMapping("/admin/reservation-rooms")
    fun readReservationRoomsByAdmin(
        @RequestParam("page", defaultValue = "1") page: Int,
        @RequestParam("size", defaultValue = "10") size: Int,
    ): ResponseDto<*> {
        if (page < 1 || size < 1) {
            throw org.springframework.web.bind.MissingRequestValueException("page or size")
        }

        val requestId: Long = 2
        return ResponseDto.ok(reservationService.readReservationRooms(requestId, page, size))
    }

    @GetMapping("/admin/reservation-rooms/{reservationRoomId}")
    fun readReservationRoomDetailByAdmin(
        @PathVariable("reservationRoomId") reservationRoomId: Long,
    ): ResponseDto<*> {
        val requestId: Long = 2
        return ResponseDto.ok(reservationService.readReservationRoomDetail(requestId, reservationRoomId))
    }

    @DeleteMapping("/admin/reservation-rooms/{reservationRoomId}")
    fun deleteReservationRoomByAdmin(
        @PathVariable("reservationRoomId") reservationRoomId: Long,
    ): ResponseDto<*> {
        val requestId: Long = 2
        return ResponseDto.ok(reservationService.deleteReservationRoom(requestId, reservationRoomId))
    }

    @PatchMapping("/admin/reservation-rooms/{reservationRoomId}")
    fun updateReservationRoomStatusByAdmin(
        @PathVariable("reservationRoomId") reservationRoomId: Long,
        @RequestBody @Valid requestBody: ReservationStatusDto,
    ): ResponseDto<*> {
        return ResponseDto.ok(reservationService.updateReservationRoomStatus(reservationRoomId, requestBody))
    }

    @PatchMapping("/admin/reservation-amenities/{reservationAmenityId}")
    fun updateReservationAmenityStatusByAdmin(
        @PathVariable("reservationAmenityId") reservationAmenityId: Long,
        @RequestBody @Valid requestBody: ReservationStatusDto,
    ): ResponseDto<*> {
        return ResponseDto.ok(reservationService.updateReservationAmenityStatus(reservationAmenityId, requestBody))
    }
}
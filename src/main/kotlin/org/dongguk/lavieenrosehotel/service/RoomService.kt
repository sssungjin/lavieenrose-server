package org.dongguk.lavieenrosehotel.service

import org.dongguk.lavieenrosehotel.domain.Room
import org.dongguk.lavieenrosehotel.domain.RoomOption
import org.dongguk.lavieenrosehotel.dto.common.PageInfo
import org.dongguk.lavieenrosehotel.dto.request.RoomDto
import org.dongguk.lavieenrosehotel.dto.request.StatusDto
import org.dongguk.lavieenrosehotel.dto.response.RoomDetailDto
import org.dongguk.lavieenrosehotel.dto.response.RoomListDto
import org.dongguk.lavieenrosehotel.exception.CommonException
import org.dongguk.lavieenrosehotel.exception.ErrorCode
import org.dongguk.lavieenrosehotel.repository.CategoryRepository
import org.dongguk.lavieenrosehotel.repository.RoomOptionRepository
import org.dongguk.lavieenrosehotel.repository.RoomRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RoomService(
    private val roomRepository: RoomRepository,
    private val roomOptionRepository: RoomOptionRepository,
    private val categoryRepository: CategoryRepository
) {
    @Transactional
    fun createRoom(requestBody: RoomDto): RoomDetailDto {
        val category = categoryRepository.findById(requestBody.categoryId)
            .orElseThrow { throw CommonException(ErrorCode.NOT_FOUND) }

        val room = roomRepository.saveAndFlush(
            Room(
                _floor = requestBody.floor,
                _roomNumber = requestBody.number,
                _size = requestBody.size,
                _status = requestBody.status,
                _bedroomCnt = requestBody.bedroomCnt,
                _bathroomCnt = requestBody.bathroomCnt,
                _livingRoomCnt = requestBody.livingRoomCnt,
                _kitchenCnt = requestBody.kitchenCnt,
                _terraceCnt = requestBody.terraceCnt,
                _bedType = requestBody.bedType,
                _bedCnt = requestBody.bedCnt,

                _category = category)
        )

        val options = roomOptionRepository.save(
            RoomOption(
                _room = room,
                _allowSmoking = requestBody.options[0],
                _allowCooking = requestBody.options[1],
                _existKitchenware = requestBody.options[2],
                _existMicrowave = requestBody.options[3],
                _existRefrigerator = requestBody.options[4],
                _existElectricKettle = requestBody.options[5],
                _existTv = requestBody.options[6],
                _existProjector = requestBody.options[7],
                _existWaterPurifier = requestBody.options[8],
                _existCoffeeMachine = requestBody.options[9],
                _existAirConditioner = requestBody.options[10],
                _existHairDryer = requestBody.options[11],
                _existSwimmingPool = requestBody.options[12],
                _existBathtub = requestBody.options[13],
                _existBidet = requestBody.options[14],
                _existWhirlpoolSpa = requestBody.options[15]
            )
        )

        return RoomDetailDto.fromEntity(room, options)
    }

    fun readRooms(
        floor: Int,
        page: Int,
        size: Int
    ): Map<String, Any> {
        val pageable: Pageable = if (floor == 0) {
            PageRequest.of(page - 1, size, Sort.by(
                Sort.Order.asc("floor"),
                Sort.Order.asc("number")
            ))
        } else {
            PageRequest.of(page - 1, size, Sort.by("number").ascending())
        }

        val pages: Page<RoomRepository.ListForm> = if (floor == 0) {
            roomRepository.findListForm(pageable)
        } else {
            roomRepository.findListFormByFloor(floor, pageable)
        }

        return mapOf(
                "page_info" to PageInfo.of(pages),
                "rooms" to pages.content.map { RoomListDto.fromEntity(it) }
        )

    }

    fun readRoom(roomId: Long): RoomDetailDto {
        val room = roomRepository.findBy_id(roomId)
            .orElseThrow { throw CommonException(ErrorCode.NOT_FOUND) }

        return RoomDetailDto.fromEntity(room)
    }

    @Transactional
    fun updateRoomStatus(roomId: Long, requestBody: StatusDto) {
        val room = roomRepository.findById(roomId)
            .orElseThrow { throw CommonException(ErrorCode.NOT_FOUND) }

        room.updateStatus(requestBody)
    }

    @Transactional
    fun updateRoom(roomId: Long, requestBody: RoomDto) {
        val room = roomRepository.findById(roomId)
            .orElseThrow { throw CommonException(ErrorCode.NOT_FOUND) }

        val category = categoryRepository.findById(requestBody.categoryId)
            .orElseThrow { throw CommonException(ErrorCode.NOT_FOUND) }

        room.apply {
            updateRoom(category, requestBody)
            roomOption?.update(requestBody.options)
        }
    }

    @Transactional
    fun deleteRoom(roomId: Long) {
        val room = roomRepository.findById(roomId)
            .orElseThrow { throw CommonException(ErrorCode.NOT_FOUND) }

        roomRepository.delete(room)
    }
}

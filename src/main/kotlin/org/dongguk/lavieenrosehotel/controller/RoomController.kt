package org.dongguk.lavieenrosehotel.controller

import org.dongguk.lavieenrosehotel.dto.common.ResponseDto
import org.dongguk.lavieenrosehotel.dto.request.RoomDto
import org.dongguk.lavieenrosehotel.dto.request.StatusDto
import org.dongguk.lavieenrosehotel.exception.CommonException
import org.dongguk.lavieenrosehotel.exception.ErrorCode
import org.dongguk.lavieenrosehotel.service.RoomService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class RoomController(
    private val roomService: RoomService,
) {

    @PostMapping("/admin/rooms")
    fun createRoom(
        @RequestBody requestBody: RoomDto,
    ): ResponseDto<*> {
        if (requestBody.options.size != 16) {
            throw CommonException(ErrorCode.BAD_REQUEST)
        }

        return ResponseDto.created(roomService.createRoom(requestBody))
    }

    @GetMapping("/admin/rooms")
    fun readRooms(
        @RequestParam("floor", defaultValue = "0") floor: Int,
        @RequestParam("page") page: Int,
        @RequestParam("size") size: Int,
    ): ResponseDto<*> {
        if (page < 1 || size < 1) {
            throw CommonException(ErrorCode.INVALID_ARGUMENT)
        }

        return ResponseDto.ok(roomService.readRooms(floor, page, size))
    }

    @GetMapping("/admin/rooms/{roomId}")
    fun readRooms(
        @PathVariable roomId: Long,
    ) = ResponseDto.ok(roomService.readRoom(roomId))

    @PatchMapping("/admin/rooms/{roomId}")
    fun updateRoomStatus(
        @PathVariable roomId: Long,
        @RequestBody requestBody: StatusDto,
    ) = ResponseDto.ok(roomService.updateRoomStatus(roomId, requestBody))

    @PutMapping("/admin/rooms/{roomId}")
    fun updateRoom(
        @PathVariable roomId: Long,
        @RequestBody requestBody: RoomDto,
    ): ResponseDto<*> {
        if (requestBody.options.size != 16) {
            throw CommonException(ErrorCode.BAD_REQUEST)
        }

        return ResponseDto.ok(roomService.updateRoom(roomId, requestBody))
    }

    @DeleteMapping("/admin/rooms/{roomId}")
    fun deleteRoom(
        @PathVariable roomId: Long,
    ) = ResponseDto.ok(roomService.deleteRoom(roomId))
}
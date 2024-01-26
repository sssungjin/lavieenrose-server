package org.dongguk.lavieenrosehotel.dto.response

import com.fasterxml.jackson.annotation.JsonProperty
import org.dongguk.lavieenrosehotel.domain.Room
import org.dongguk.lavieenrosehotel.repository.RoomRepository
import org.jetbrains.annotations.NotNull

class RoomListDto(
    @JsonProperty("id")
    @field:NotNull
    val id: Long,

    @JsonProperty("floor_number")
    @field:NotNull
    val floorNumber: String,

    @JsonProperty("status")
    @field:NotNull
    val status: String,
) {
    companion object {
        fun fromEntity(room: RoomRepository.ListForm): RoomListDto {
            return RoomListDto(
                id = room.id,
                // String floor 뒤에 2글자 숫자로 number를 붙여서 출력
                floorNumber = "${room.floor}${room.number.toString().padStart(2, '0')}",
                status = room.status
            )
        }
    }
}
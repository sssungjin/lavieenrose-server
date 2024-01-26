package org.dongguk.lavieenrosehotel.dto.response

import com.fasterxml.jackson.annotation.JsonProperty
import org.dongguk.lavieenrosehotel.domain.Room

class AvailableRoomListDto(
    val id: Long,

    @JsonProperty("room_type")
    val roomType: String,

    @JsonProperty("category_name")
    val categoryName: String,

    @JsonProperty("max_capacity")
    val maxCapacity: Int,

    @JsonProperty("standard_capacity")
    val standardCapacity: Int,

    @JsonProperty("price")
    val price: Int
) {
    companion object {
        fun fromEntity(room: Room): AvailableRoomListDto {
            return AvailableRoomListDto(
                id = room.id!!,
                roomType = room.category!!.roomType.value,
                categoryName = room.category!!.name,
                maxCapacity = room.category!!.maxCapacity,
                standardCapacity = room.category!!.standardCapacity,
                price = room.category!!.defaultPrice
            )
        }
    }
}

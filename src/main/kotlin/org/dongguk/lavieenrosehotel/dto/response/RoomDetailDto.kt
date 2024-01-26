package org.dongguk.lavieenrosehotel.dto.response

import com.fasterxml.jackson.annotation.JsonProperty
import org.dongguk.lavieenrosehotel.domain.Room
import org.dongguk.lavieenrosehotel.domain.RoomOption
import org.dongguk.lavieenrosehotel.dto.type.EVisibleStatus
import org.jetbrains.annotations.NotNull

class RoomDetailDto(
    @JsonProperty("id")
    @field:NotNull
    val id: Long,

    @JsonProperty("number")
    @field:NotNull
    val number: Int,

    @JsonProperty("floor")
    @field:NotNull
    val floor: Int,

    @JsonProperty("size")
    @field:NotNull
    val size: Double,

    @JsonProperty("status")
    @field:NotNull
    val status: EVisibleStatus,

    @JsonProperty("bedroom_cnt")
    @field:NotNull
    val bedroomCnt: Int,

    @JsonProperty("bathroom_cnt")
    @field:NotNull
    val bathroomCnt: Int,

    @JsonProperty("living_room_cnt")
    @field:NotNull
    val livingRoomCnt: Int,

    @JsonProperty("kitchen_cnt")
    @field:NotNull
    val kitchenCnt: Int,

    @JsonProperty("terrace_cnt")
    @field:NotNull
    val terraceCnt: Int,

    @JsonProperty("bed_type")
    @field:NotNull
    val bedType: String,

    @JsonProperty("bed_cnt")
    @field:NotNull
    val bedCnt: Int,

    @JsonProperty("options")
    @field:NotNull
    val options: List<Boolean>,
) {

    companion object {
        fun fromEntity(room: Room, options: RoomOption): RoomDetailDto {
            return RoomDetailDto(
                id = room.id!!,
                number = room.roomNumber,
                floor = room.floor,
                size = room.size,
                status = room.status,
                bedroomCnt = room.bedroomCnt,
                bathroomCnt = room.bathroomCnt,
                livingRoomCnt = room.livingRoomCnt,
                kitchenCnt = room.kitchenCnt,
                terraceCnt = room.terraceCnt,
                bedType = room.bedType.value,
                bedCnt = room.bedCnt,
                options = listOf(
                    options.allowSmoking,
                    options.allowCooking,
                    options.existKitchenware,
                    options.existMicrowave,
                    options.existRefrigerator,
                    options.existElectricKettle,
                    options.existTv,
                    options.existProjector,
                    options.existWaterPurifier,
                    options.existCoffeeMachine,
                    options.existAirConditioner,
                    options.existHairDryer,
                    options.existSwimmingPool,
                    options.existBathtub,
                    options.existBidet,
                    options.existWhirlpoolSpa,
                )
            )
        }

        fun fromEntity(room: Room): RoomDetailDto {
            return RoomDetailDto(
                id = room.id!!,
                number = room.roomNumber,
                floor = room.floor,
                size = room.size,
                status = room.status,
                bedroomCnt = room.bedroomCnt,
                bathroomCnt = room.bathroomCnt,
                livingRoomCnt = room.livingRoomCnt,
                kitchenCnt = room.kitchenCnt,
                terraceCnt = room.terraceCnt,
                bedType = room.bedType.value,
                bedCnt = room.bedCnt,
                options = listOf(
                    room.roomOption!!.allowSmoking,
                    room.roomOption!!.allowCooking,
                    room.roomOption!!.existKitchenware,
                    room.roomOption!!.existMicrowave,
                    room.roomOption!!.existRefrigerator,
                    room.roomOption!!.existElectricKettle,
                    room.roomOption!!.existTv,
                    room.roomOption!!.existProjector,
                    room.roomOption!!.existWaterPurifier,
                    room.roomOption!!.existCoffeeMachine,
                    room.roomOption!!.existAirConditioner,
                    room.roomOption!!.existHairDryer,
                    room.roomOption!!.existSwimmingPool,
                    room.roomOption!!.existBathtub,
                    room.roomOption!!.existBidet,
                    room.roomOption!!.existWhirlpoolSpa,
                )
            )
        }
    }
}
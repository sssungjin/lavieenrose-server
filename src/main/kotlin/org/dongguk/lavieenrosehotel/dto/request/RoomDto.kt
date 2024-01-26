package org.dongguk.lavieenrosehotel.dto.request

import com.fasterxml.jackson.annotation.JsonProperty
import org.dongguk.lavieenrosehotel.dto.type.EBedType
import org.dongguk.lavieenrosehotel.dto.type.EVisibleStatus
import org.jetbrains.annotations.NotNull

class RoomDto(
    @JsonProperty("category_id")
    @field:NotNull
    val categoryId: Long,

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
    val bedType: EBedType,

    @JsonProperty("bed_cnt")
    @field:NotNull
    val bedCnt: Int,

    @JsonProperty("options")
    @field:NotNull
    val options: List<Boolean>,
) {
}
package org.dongguk.lavieenrosehotel.dto.request

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.Size
import org.dongguk.lavieenrosehotel.dto.type.ERoomType
import org.dongguk.lavieenrosehotel.dto.type.EViewType
import org.dongguk.lavieenrosehotel.dto.type.EVisibleStatus
import org.jetbrains.annotations.NotNull

class CategoryDto(
    @JsonProperty("name")
    @field:NotNull
    val name: String,

    @JsonProperty("room_type")
    @field:NotNull
    val roomType: ERoomType,

    @JsonProperty("view_type")
    @field:NotNull
    val viewType: EViewType,

    @JsonProperty("summary")
    @field:Size(min = 1, max = 100, message = "1~50자 사이로 입력해주세요.")
    val summary: String,

    @JsonProperty("standard_capacity")
    @field:NotNull
    val standardCapacity: Int,

    @JsonProperty("max_capacity")
    @field:NotNull
    val maxCapacity: Int,

    @JsonProperty("default_price")
    @field:NotNull
    val defaultPrice: Int,

    @JsonProperty("peek_price")
    @field:NotNull
    val peekPrice: Int,

    @JsonProperty("addition_price")
    @field:NotNull
    val additionPrice: Int,

    @JsonProperty("status")
    @field:NotNull
    val status: EVisibleStatus,
) {
}
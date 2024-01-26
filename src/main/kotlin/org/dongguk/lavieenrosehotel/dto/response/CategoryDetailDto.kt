package org.dongguk.lavieenrosehotel.dto.response

import com.fasterxml.jackson.annotation.JsonProperty
import org.dongguk.lavieenrosehotel.domain.Category
import org.dongguk.lavieenrosehotel.domain.Image
import org.dongguk.lavieenrosehotel.dto.type.EVisibleStatus
import org.jetbrains.annotations.NotNull

class CategoryDetailDto(
    @JsonProperty("id")
    @field:NotNull
    val id: Long,

    @JsonProperty("name")
    @field:NotNull
    val name: String,

    @JsonProperty("room_type")
    @field:NotNull
    val roomType: String,

    @JsonProperty("view_type")
    @field:NotNull
    val viewType: String,

    @JsonProperty("summary")
    @field:NotNull
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

    @JsonProperty("images")
    val imageNames: List<String>,
) {

    companion object {
        fun fromEntity(
            category: Category,
            images: List<Image>,
        ): CategoryDetailDto {
            return CategoryDetailDto(
                id = category.id!!,
                name = category.name,
                roomType = category.roomType.name,
                viewType = category.viewType.name,
                summary = category.summary,
                standardCapacity = category.standardCapacity,
                maxCapacity = category.maxCapacity,
                defaultPrice = category.defaultPrice,
                peekPrice = category.peekPrice,
                additionPrice = category.additionPrice,
                status = category.status,
                imageNames = images.map { it.url }
            )
        }
    }
}
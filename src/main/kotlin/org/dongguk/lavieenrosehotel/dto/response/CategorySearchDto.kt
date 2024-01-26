package org.dongguk.lavieenrosehotel.dto.response

import com.fasterxml.jackson.annotation.JsonProperty
import org.dongguk.lavieenrosehotel.domain.Category

class CategorySearchDto(
    @JsonProperty("id")
    val id: Long,

    @JsonProperty("name")
    val name: String,

    @JsonProperty("room_type")
    val roomType: String,

    @JsonProperty("view_type")
    val viewType: String,
) {
    companion object {
        fun fromEntity(category: Category): CategorySearchDto {
            return CategorySearchDto(
                id = category.id!!,
                name = category.name,
                roomType = category.roomType.value,
                viewType = category.viewType.value,
            )
        }
    }
}
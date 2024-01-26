package org.dongguk.lavieenrosehotel.dto.response

import com.fasterxml.jackson.annotation.JsonProperty
import org.dongguk.lavieenrosehotel.domain.Amenity
import org.dongguk.lavieenrosehotel.domain.Image
import org.dongguk.lavieenrosehotel.dto.type.EAmenityType
import java.time.LocalDate

class AmenityDetailDto (
    val id: Long,

    @JsonProperty("type")
    val type: EAmenityType,

    @JsonProperty("name")
    val name: String,

    @JsonProperty("summary")
    val summary: String,

    @JsonProperty("information")
    val information: String,

    @JsonProperty("all_day_price")
    val allDayPrice: Int,

    @JsonProperty("images")
    val images: List<String>,

    @JsonProperty("is_visible")
    val isVisible: Boolean,
    ) {
    companion object {
        fun fromEntity(amenity: Amenity): AmenityDetailDto {
            return AmenityDetailDto(
                id = amenity.id!!,
                type = amenity.type,
                name = amenity.name,
                summary = amenity.summary,
                information = amenity.information,
                allDayPrice = amenity.allDayPrice,
                images = amenity.images.map { it.url },
                // 현재일 기준 12월에서 3월 사이에는 미노출
                isVisible = if (amenity.type == EAmenityType.SKI) {
                    LocalDate.now().monthValue !in 4..11
                } else {
                    true
                }
            )
        }

        fun fromEntity(amenity: Amenity, images: List<Image>): AmenityDetailDto {
            return AmenityDetailDto(
                id = amenity.id!!,
                type = amenity.type,
                name = amenity.name,
                summary = amenity.summary,
                information = amenity.information,
                allDayPrice = amenity.allDayPrice,
                images = images.map { it.url },
                // 현재일 기준 12월에서 3월 사이에는 미노출
                isVisible = if (amenity.type == EAmenityType.SKI) {
                    LocalDate.now().monthValue !in 4..11
                } else {
                    true
                }
            )
        }
    }
}
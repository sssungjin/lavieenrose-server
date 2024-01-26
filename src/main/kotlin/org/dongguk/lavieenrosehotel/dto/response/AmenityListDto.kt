package org.dongguk.lavieenrosehotel.dto.response

import com.fasterxml.jackson.annotation.JsonProperty
import org.dongguk.lavieenrosehotel.domain.Amenity
import org.dongguk.lavieenrosehotel.dto.type.EAmenityType
import java.time.LocalDate

class AmenityListDto (
    val id: Long,

    @JsonProperty("amenity_type")
    val amenityType: EAmenityType,

    @JsonProperty("is_exposed")
    val isExposed: Boolean,
    ) {
    companion object {
        fun fromEntity(amenity: Amenity): AmenityListDto {
            return AmenityListDto(
                id = amenity.id!!,
                amenityType = amenity.type,
                isExposed = if (amenity.type == EAmenityType.SKI) {
                    LocalDate.now().monthValue !in 4..11
                } else {
                    true
                }
            )
        }
    }
}
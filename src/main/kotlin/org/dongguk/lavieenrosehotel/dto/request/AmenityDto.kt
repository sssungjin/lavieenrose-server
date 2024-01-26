package org.dongguk.lavieenrosehotel.dto.request

import com.fasterxml.jackson.annotation.JsonProperty
import org.dongguk.lavieenrosehotel.dto.type.EAmenityType

class AmenityDto (
    @JsonProperty("amenity_type")
    val amenityType: EAmenityType,

    @JsonProperty("name")
    val name: String,

    @JsonProperty("summary")
    val summary: String,

    @JsonProperty("information")
    val information: String,

    @JsonProperty("all_day_price")
    val allDayPrice: Int,
) {
}
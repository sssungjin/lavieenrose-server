package org.dongguk.lavieenrosehotel.dto.response

import com.fasterxml.jackson.annotation.JsonProperty

class ReservationAmenityPriceDto (
    @JsonProperty("total_price")
    val totalPrice: Int,
) {
    companion object {
        fun fromEntity(totalPrice: Int): ReservationAmenityPriceDto {
            return ReservationAmenityPriceDto(
                totalPrice = totalPrice
            )
        }
    }
}
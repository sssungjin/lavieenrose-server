package org.dongguk.lavieenrosehotel.dto.response

import com.fasterxml.jackson.annotation.JsonProperty

class ReservationRoomPriceDto (
    @JsonProperty("total_price")
    val totalPrice: Int,
){
    companion object {
        fun fromValue(totalPrice: Int): ReservationRoomPriceDto {
            return ReservationRoomPriceDto(
                totalPrice = totalPrice
            )
        }
    }
}
package org.dongguk.lavieenrosehotel.dto.response

import com.fasterxml.jackson.annotation.JsonProperty
import org.dongguk.lavieenrosehotel.domain.ReservationAmenity
import org.dongguk.lavieenrosehotel.dto.type.EReservationStatus
import java.time.LocalDate

class ReservationAmenityListDto (
    val id: Long,
    val status: EReservationStatus,

    @JsonProperty("amenity_name")
    val amenityName: String,

    @JsonProperty("start_date")
    val startDate: String,

    @JsonProperty("adult_cnt")
    val adultCnt: Int,

    @JsonProperty("teenager_cnt")
    val teenagerCnt: Int,

    @JsonProperty("child_cnt")
    val childCnt: Int,

    @JsonProperty("payment_date")
    val paymentDate: String,

    @JsonProperty("total_price")
    val totalPrice: Int,

    @JsonProperty("all_day_price")
    val allDayPrice: Int,

) {
    companion object {
        fun fromEntity(reservationAmenity: ReservationAmenity): ReservationAmenityListDto {
            return ReservationAmenityListDto(
                id = reservationAmenity.id!!,
                status = reservationAmenity.status,
                amenityName = reservationAmenity.amenity.name,
                startDate = reservationAmenity.startDate.toString(),
                adultCnt = reservationAmenity.adultCnt,
                teenagerCnt = reservationAmenity.teenagerCnt,
                childCnt = reservationAmenity.childCnt,
                paymentDate = LocalDate.now().toString(),
                totalPrice = reservationAmenity.paymentPrice,
                allDayPrice = reservationAmenity.amenity.allDayPrice
            )
        }
    }
}
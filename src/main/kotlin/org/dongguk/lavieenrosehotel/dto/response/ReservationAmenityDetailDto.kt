package org.dongguk.lavieenrosehotel.dto.response

import com.fasterxml.jackson.annotation.JsonProperty
import org.dongguk.lavieenrosehotel.domain.ReservationAmenity
import org.dongguk.lavieenrosehotel.dto.type.EReservationStatus

class ReservationAmenityDetailDto (
    val id: Long,
    val status: EReservationStatus,

    @JsonProperty("start_date")
    val startDate: String,

    @JsonProperty("payment_date")
    val paymentDate: String,

    @JsonProperty("payment_method")
    val paymentMethod: String,

    @JsonProperty("total_price")
    val totalPrice: Int,

    @JsonProperty("reservation_name")
    val reservationName: String,

    @JsonProperty("reservation_phone_number")
    val reservationPhoneNumber: String,

    @JsonProperty("adult_cnt")
    val adultCnt: Int,

    @JsonProperty("teenager_cnt")
    val teenagerCnt: Int,

    @JsonProperty("child_cnt")
    val childCnt: Int,

    @JsonProperty("amenity_name")
    val amenityName: String,

    @JsonProperty("amenity_information")
    val amenityInformation: String,

) {
    companion object {
        fun fromEntity(reservationAmenity: ReservationAmenity): ReservationAmenityDetailDto {
            return ReservationAmenityDetailDto(
                id = reservationAmenity.id!!,
                status = reservationAmenity.status,
                startDate = reservationAmenity.startDate.toString(),
                paymentDate = reservationAmenity.paymentHistories!!.paymentDate.toString(),
                paymentMethod = reservationAmenity.paymentHistories!!.paymentMethod.value,
                totalPrice = reservationAmenity.paymentHistories!!.paymentPrice,
                reservationName = reservationAmenity.reservationName,
                reservationPhoneNumber = reservationAmenity.reservationPhoneNumber,
                adultCnt = reservationAmenity.adultCnt,
                teenagerCnt = reservationAmenity.teenagerCnt,
                childCnt = reservationAmenity.childCnt,
                amenityName = reservationAmenity.amenity.name,
                amenityInformation = reservationAmenity.amenity.information
            )
        }
    }

}
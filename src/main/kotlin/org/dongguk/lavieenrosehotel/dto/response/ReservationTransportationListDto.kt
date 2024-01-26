package org.dongguk.lavieenrosehotel.dto.response

import com.fasterxml.jackson.annotation.JsonProperty
import org.dongguk.lavieenrosehotel.domain.PaymentHistory
import org.dongguk.lavieenrosehotel.domain.ReservationTransportation
import org.dongguk.lavieenrosehotel.dto.request.ReservationTransportationDto
import org.dongguk.lavieenrosehotel.dto.type.EReservationStatus
import java.time.LocalDate

class ReservationTransportationListDto(
    val id: Long,
    val status: EReservationStatus,

    @JsonProperty("start_end_location") val startEndLocation: String,
    @JsonProperty("start_date") val startDate: String,
    @JsonProperty("end_date") val endDate: String,

    @JsonProperty("max_capacity") val maxCapacity: Int,

    @JsonProperty("payment_date") val paymentDate: String,
    @JsonProperty("payment_method") val paymentMethod: String,
    @JsonProperty("total_price") val totalPrice: Int,
) {
    companion object {
        fun fromEntity(
            reservationTransportation: ReservationTransportation,
            responseBody: ReservationTransportationDto, ): ReservationTransportationListDto {
            return ReservationTransportationListDto(
                id = reservationTransportation.id!!,
                status = reservationTransportation.status,
                startEndLocation = reservationTransportation.locationName,
                startDate = reservationTransportation.startDate.toString(),
                endDate = reservationTransportation.endDate.toString(),
                maxCapacity = reservationTransportation.type.capacity,
                paymentDate = LocalDate.now().toString(),
                paymentMethod = responseBody.method.value,
                totalPrice = reservationTransportation.paymentPrice
            )
        }

        fun fromEntity(reservationTransportation: ReservationTransportation): ReservationTransportationListDto {
            return ReservationTransportationListDto(
                id = reservationTransportation.id!!,
                status = reservationTransportation.status,
                startEndLocation = reservationTransportation.locationName,
                startDate = reservationTransportation.startDate.toString(),
                endDate = reservationTransportation.endDate.toString(),
                maxCapacity = reservationTransportation.type.capacity,
                paymentDate = LocalDate.now().toString(),
                paymentMethod = reservationTransportation.paymentHistories!!.paymentMethod.value,
                totalPrice = reservationTransportation.paymentPrice
            )
        }
    }
}
package org.dongguk.lavieenrosehotel.dto.response

import com.fasterxml.jackson.annotation.JsonProperty
import org.dongguk.lavieenrosehotel.domain.BreakfastOrder
import org.dongguk.lavieenrosehotel.domain.ReservationRoom
import org.dongguk.lavieenrosehotel.dto.type.EReservationStatus
import org.dongguk.lavieenrosehotel.dto.type.ERoomType

class ReservationRoomDetailDto (
    val id: Long,
    val status: EReservationStatus,

    @JsonProperty("start_date")
    val startDate: String,

    @JsonProperty("end_date")
    val endDate: String,

    @JsonProperty("max_capacity")
    val maxCapacity: Int,

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

    @JsonProperty("category_name")
    val categoryName: String,

    @JsonProperty("room_type")
    val roomType: ERoomType,

    @JsonProperty("floor")
    val floor: Int,

    @JsonProperty("room_number")
    val roomNumber: Int,

    @JsonProperty("breakfast_orders")
    val breakfastOrders: List<Boolean>,
    ) {
    companion object {
        fun fromEntity(reservationRoom: ReservationRoom): ReservationRoomDetailDto {
            return ReservationRoomDetailDto(
                id = reservationRoom.id!!,
                status = reservationRoom.status,
                startDate = reservationRoom.startDate.toString(),
                endDate = reservationRoom.endDate.toString(),
                maxCapacity = reservationRoom.room.category!!.maxCapacity,
                paymentDate = reservationRoom.paymentHistories!!.paymentDate.toString(),
                paymentMethod = reservationRoom.paymentHistories!!.paymentMethod.value,
                totalPrice = reservationRoom.paymentHistories!!.paymentPrice,
                reservationName = reservationRoom.reservationName,
                reservationPhoneNumber = reservationRoom.reservationPhoneNumber,
                adultCnt = reservationRoom.adultCnt,
                teenagerCnt = reservationRoom.teenagerCnt,
                childCnt = reservationRoom.childCnt,
                categoryName = reservationRoom.room.category!!.name,
                roomType = reservationRoom.room.category!!.roomType,
                floor = reservationRoom.room.floor,
                roomNumber = reservationRoom.room.roomNumber,
                breakfastOrders = reservationRoom.breakfastOrders.map { it.isEating }
            )
        }
    }
}
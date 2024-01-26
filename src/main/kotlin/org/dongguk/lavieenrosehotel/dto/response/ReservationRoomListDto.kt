package org.dongguk.lavieenrosehotel.dto.response

import com.fasterxml.jackson.annotation.JsonProperty
import org.dongguk.lavieenrosehotel.domain.ReservationRoom
import org.dongguk.lavieenrosehotel.dto.request.ReservationRoomDto
import org.dongguk.lavieenrosehotel.dto.type.EReservationStatus
import org.dongguk.lavieenrosehotel.dto.type.ERoomType
import java.time.LocalDate

class ReservationRoomListDto (
    val id: Long,
    val status: EReservationStatus,

    @JsonProperty("room_number")
    val roomNumber: Int,

    @JsonProperty("category_name")
    val categoryName: String,

    @JsonProperty("room_type")
    val roomType: ERoomType,

    @JsonProperty("start_date")
    val startDate: String,

    @JsonProperty("end_date")
    val endDate: String,

    @JsonProperty("payment_date")
    val paymentDate: String,

    @JsonProperty("payment_method")
    val paymentMethod: String,

    @JsonProperty("payment_price")
    val paymentPrice: Int,

    @JsonProperty("total_price")
    val totalPrice: Int,

    @JsonProperty("adult_cnt")
    val adultCnt: Int,

    @JsonProperty("teenager_cnt")
    val teenagerCnt: Int,

    @JsonProperty("child_cnt")
    val childCnt: Int,
){
    companion object{
        fun fromEntity(reservationRoom: ReservationRoom, responseBody: ReservationRoomDto): ReservationRoomListDto {
            return ReservationRoomListDto(
                id = reservationRoom.id!!,
                status = reservationRoom.status,
                roomNumber = reservationRoom.room.roomNumber,
                roomType = reservationRoom.room.category!!.roomType,
                categoryName = reservationRoom.room.category!!.name,
                startDate = reservationRoom.startDate.toString(),
                endDate = reservationRoom.endDate.toString(),
                paymentDate = LocalDate.now().toString(),
                paymentMethod = responseBody.method.value,
                paymentPrice = responseBody.paymentPrice,
                totalPrice = reservationRoom.paymentPrice,
                adultCnt = reservationRoom.adultCnt,
                teenagerCnt = reservationRoom.teenagerCnt,
                childCnt = reservationRoom.childCnt
            )
        }

        fun fromEntity(reservationRoom: ReservationRoom): ReservationRoomListDto {
            return ReservationRoomListDto(
                id = reservationRoom.id!!,
                status = reservationRoom.status,
                roomNumber = reservationRoom.room.roomNumber,
                roomType = reservationRoom.room.category!!.roomType,
                categoryName = reservationRoom.room.category!!.name,
                startDate = reservationRoom.startDate.toString(),
                endDate = reservationRoom.endDate.toString(),
                paymentDate = LocalDate.now().toString(),
                paymentMethod = reservationRoom.paymentHistories!!.paymentMethod.value,
                paymentPrice = reservationRoom.paymentHistories!!.paymentPrice,
                totalPrice = reservationRoom.paymentPrice,
                adultCnt = reservationRoom.adultCnt,
                teenagerCnt = reservationRoom.teenagerCnt,
                childCnt = reservationRoom.childCnt
            )
        }
    }
}
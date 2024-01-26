package org.dongguk.lavieenrosehotel.dto.request

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import org.dongguk.lavieenrosehotel.dto.type.EPaymentMethod
import org.jetbrains.annotations.NotNull
import java.time.LocalDate

class ReservationTransportationDto(
    @JsonProperty("type_id") @field:NotNull
    val typeId: Int,

    @JsonProperty("location_name") @field:NotNull
    @Size(min = 2, max = 32, message = "출발지 이름은 2~50자 이내로 입력해주세요.")
    val locationName: String,

    @JsonProperty("start_date") @field:NotNull
    val startDate: LocalDate,

    @JsonProperty("end_date") @field:NotNull
    val endDate: LocalDate,

    @JsonProperty("reservation_name") @field:NotNull
    @Size(min = 2, max = 32, message = "예약자 이름은 2~32자 이내로 입력해주세요.")
    val reservationName: String,

    @JsonProperty("reservation_phone_number") @field:NotNull
    @field:Pattern(regexp = "[0-9]{3}-[0-9]{4}-[0-9]{4}", message = "올바른 전화번호 형식(000-0000-0000)이 아닙니다.")
    val reservationPhoneNumber: String,

    @JsonProperty("imp_uid") @field:NotNull
    @field:Size(min = 5, max = 32, message = "imp_uid는 5~32자 이내로 입력해주세요.")
    val impUid: String,

    @JsonProperty("payment_method") @field:NotNull
    val method: EPaymentMethod,

    @JsonProperty("price") @field:NotNull
    val price: Int,

    @JsonProperty("discount_price") @field:NotNull
    val discountPrice: Int,

    @JsonProperty("payment_price") @field:NotNull
    val paymentPrice: Int,
) {
}
package org.dongguk.lavieenrosehotel.dto.request

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import org.jetbrains.annotations.NotNull

class AuthSignUpDto(
    @JsonProperty("serial_id") @field:NotNull
    @field:Size(min = 5, max = 20, message = "serial_id는 5~20자 이내로 입력해주세요.")
    val serialId: String,

    @JsonProperty("password") @field:NotNull
    @field:Pattern(
        regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[@!#\$%])[A-Za-z0-9@!#\$%]{8,20}$",
        message = "비밀번호는 8~20자 이내로 입력해주세요.")
    val password: String,

    @JsonProperty("name") @field:NotNull
    @field:Size(min = 2, max = 32, message = "이름은 2~32자 이내로 입력해주세요.")
    val name: String,

    @JsonProperty("phone_number") @field:NotNull
    @field:Pattern(regexp = "[0-9]{3}-[0-9]{4}-[0-9]{4}", message = "올바른 전화번호 형식(000-0000-0000)이 아닙니다.")
    val phoneNumber: String,
) {
}
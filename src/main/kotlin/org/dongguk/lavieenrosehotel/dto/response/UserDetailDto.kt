package org.dongguk.lavieenrosehotel.dto.response

import com.fasterxml.jackson.annotation.JsonProperty
import org.dongguk.lavieenrosehotel.domain.User

class UserDetailDto(
    @JsonProperty("name") val name: String,
    @JsonProperty("phone_number") val phoneNumber: String,
    @JsonProperty("created_date") val createdDate: String,
) {
    companion object {
        fun fromEntity(user: User): UserDetailDto {
            return UserDetailDto(
                user.name!!,
                user.phoneNumber!!,
                user.createdDate.toString())
        }
    }
}
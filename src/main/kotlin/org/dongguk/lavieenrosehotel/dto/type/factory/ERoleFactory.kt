package org.dongguk.lavieenrosehotel.dto.type.factory

import org.dongguk.lavieenrosehotel.dto.type.ERole
import org.dongguk.lavieenrosehotel.exception.CommonException
import org.dongguk.lavieenrosehotel.exception.ErrorCode

object ERoleFactory {
    fun of(value: String): ERole {
        return when (value) {
            "ADMIN" -> ERole.ADMIN
            "USER" -> ERole.USER
            "GUEST" -> ERole.GUEST
            else -> throw CommonException(ErrorCode.INVALID_ROLE)
        }
    }
}
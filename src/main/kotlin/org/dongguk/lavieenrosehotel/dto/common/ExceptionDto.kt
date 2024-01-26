package org.dongguk.lavieenrosehotel.dto.common

import org.dongguk.lavieenrosehotel.exception.ErrorCode

open class ExceptionDto(private val errorCode: ErrorCode) {
    val code: Int = errorCode.code
    val message: String = errorCode.message
}


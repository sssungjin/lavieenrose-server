package org.dongguk.lavieenrosehotel.exception


class CommonException(val errorCode: ErrorCode) : RuntimeException() {
    val code: Int = errorCode.code
    override val message: String = errorCode.message
}
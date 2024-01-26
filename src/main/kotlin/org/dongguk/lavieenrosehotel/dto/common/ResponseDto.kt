package org.dongguk.lavieenrosehotel.dto.common

import com.fasterxml.jackson.annotation.JsonIgnore
import org.dongguk.lavieenrosehotel.exception.CommonException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException


class ResponseDto<T>(
    @JsonIgnore val httpStatus: HttpStatus,
    val success: Boolean,
    val data: T?,
    val error: ExceptionDto?
) {
    companion object {
        fun <T> ok(data: T): ResponseDto<T> {
            return ResponseDto(HttpStatus.OK, true, data, null)
        }

        fun <T> created(data: T): ResponseDto<T> {
            return ResponseDto(HttpStatus.CREATED, true, data, null)
        }

        fun fail(e: CommonException): ResponseDto<Any?> {
            return ResponseDto(e.errorCode.httpStatus, false, null, ExceptionDto(e.errorCode))
        }

        fun fail(e: MethodArgumentNotValidException): ResponseDto<Any?> {
            return ResponseDto(HttpStatus.BAD_REQUEST, false, null, ArgumentNotValidExceptionDto(e))
        }
    }
}
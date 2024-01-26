package org.dongguk.lavieenrosehotel.dto.common

import org.dongguk.lavieenrosehotel.exception.ErrorCode
import org.springframework.validation.FieldError
import org.springframework.validation.ObjectError
import org.springframework.web.bind.MethodArgumentNotValidException
import java.util.function.Consumer


class ArgumentNotValidExceptionDto(private val methodArgumentNotValidException: MethodArgumentNotValidException,) :
    ExceptionDto(ErrorCode.INVALID_ARGUMENT) {
    val errorFields: MutableMap<String, String?>

    init {
        errorFields = HashMap()
        methodArgumentNotValidException.bindingResult
            .allErrors.forEach(Consumer { e: ObjectError ->
                errorFields[(e as FieldError).field] = e.getDefaultMessage()
            })
    }
}
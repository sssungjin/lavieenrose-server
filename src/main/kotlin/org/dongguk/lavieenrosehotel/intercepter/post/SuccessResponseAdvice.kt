package org.dongguk.lavieenrosehotel.intercepter.post

import org.dongguk.lavieenrosehotel.dto.common.ResponseDto
import org.springframework.core.MethodParameter
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice


@RestControllerAdvice(basePackages = ["org.dongguk.lavieenrosehotel"])
class SuccessResponseAdvice : ResponseBodyAdvice<Any> {
    override fun supports(returnType: MethodParameter, converterType: Class<out HttpMessageConverter<*>>): Boolean {
        return true
    }

    override fun beforeBodyWrite(body: Any?, returnType: MethodParameter, selectedContentType: MediaType,
                                 selectedConverterType: Class<out HttpMessageConverter<*>>,
                                 request: ServerHttpRequest, response: ServerHttpResponse): Any? {
        if (returnType.parameterType == ResponseDto::class.java) {
            val status = (body as ResponseDto<*>).httpStatus
            response.setStatusCode(status)
        }

        return body
    }
}
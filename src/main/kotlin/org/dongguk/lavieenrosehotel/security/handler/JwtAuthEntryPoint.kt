package org.dongguk.lavieenrosehotel.security.handler

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.dongguk.lavieenrosehotel.dto.response.JwtErrorDto
import org.dongguk.lavieenrosehotel.exception.ErrorCode
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component


@Component
class JwtAuthEntryPoint : JwtErrorDto(), AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException,
    ) {
        if (request.getAttribute("exception") != null) {
            setErrorResponse(response, request.getAttribute("exception") as ErrorCode?)
        } else {
            setErrorResponse(response, ErrorCode.NOT_END_POINT)
        }
    }
}
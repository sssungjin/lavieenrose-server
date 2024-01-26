package org.dongguk.lavieenrosehotel.security.handler

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.dongguk.lavieenrosehotel.dto.response.JwtErrorDto
import org.dongguk.lavieenrosehotel.exception.ErrorCode
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component


@Component
class JwtAccessDeniedHandler : JwtErrorDto(), AccessDeniedHandler {
    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        accessDeniedException: org.springframework.security.access.AccessDeniedException,
    ) {
        setErrorResponse(response, ErrorCode.ACCESS_DENIED)
    }
}
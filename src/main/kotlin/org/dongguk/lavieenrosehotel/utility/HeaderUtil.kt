package org.dongguk.lavieenrosehotel.utility

import jakarta.servlet.http.HttpServletRequest
import org.dongguk.lavieenrosehotel.exception.CommonException
import org.dongguk.lavieenrosehotel.exception.ErrorCode
import org.springframework.util.StringUtils

object HeaderUtil {
    private const val HEADER_AUTHORIZATION = "Authorization"

    fun refineAuthenticationHeader(httpServletRequest: HttpServletRequest, preString: String): String {
        return httpServletRequest.getHeader(HEADER_AUTHORIZATION).let {
            if (!StringUtils.hasText(it) || !it.startsWith(preString)) {
                throw CommonException(ErrorCode.INVALID_AUTHENTICATION_HEADER)
            } else {
                it.substring(preString.length)
            }
        }
    }
}
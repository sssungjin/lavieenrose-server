package org.dongguk.lavieenrosehotel.security.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Order(1)
@Component
class GlobalLoggerFilter: OncePerRequestFilter() {
    private val log by lazy { org.slf4j.LoggerFactory.getLogger(GlobalLoggerFilter::class.java) }
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        log.info("[Global] HTTP Request Received! ({} {} {})",
            request.getHeader("X-FORWARDED-FOR") ?: request.remoteAddr,
            request.method,
            request.requestURI)

        request.setAttribute("INTERCEPTOR_PRE_HANDLE_TIME",  System.currentTimeMillis())

        filterChain.doFilter(request, response)

        val preHandleTime = request.getAttribute("INTERCEPTOR_PRE_HANDLE_TIME") as Long
        val postHandleTime = System.currentTimeMillis()

        log.info("[Global] HTTP Request Has Been Processed! It Tokes {}ms. ({} {} {})",
            postHandleTime - preHandleTime,
            request.getHeader("X-FORWARDED-FOR") ?: request.remoteAddr,
            request.method,
            request.requestURI);
    }

}
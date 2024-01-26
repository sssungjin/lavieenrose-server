package org.dongguk.lavieenrosehotel.security.filter

import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.dongguk.lavieenrosehotel.constraint.Constants
import org.dongguk.lavieenrosehotel.exception.CommonException
import org.dongguk.lavieenrosehotel.exception.ErrorCode
import org.springframework.web.filter.OncePerRequestFilter
import java.util.*


class JwtExceptionFilter: OncePerRequestFilter() {
    private val log by lazy { org.slf4j.LoggerFactory.getLogger(JwtExceptionFilter::class.java) }
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        response.characterEncoding = "UTF-8"

        try {
            filterChain.doFilter(request, response)
        } catch (e: SecurityException) {
            log.error("FilterException throw SecurityException Exception : {}", e.message)
            request.setAttribute("exception", ErrorCode.ACCESS_DENIED)
            filterChain.doFilter(request, response)
        } catch (e: MalformedJwtException) {
            log.error("FilterException throw MalformedJwtException Exception : {}", e.message)
            request.setAttribute("exception", ErrorCode.TOKEN_MALFORMED_ERROR)
            filterChain.doFilter(request, response)
        } catch (e: IllegalArgumentException) {
            log.error("FilterException throw IllegalArgumentException Exception : {}", e.message)
            request.setAttribute("exception", ErrorCode.TOKEN_TYPE_ERROR)
            filterChain.doFilter(request, response)
        } catch (e: ExpiredJwtException) {
            log.error("FilterException throw ExpiredJwtException Exception : {}", e.message)
            request.setAttribute("exception", ErrorCode.EXPIRED_TOKEN_ERROR)
            filterChain.doFilter(request, response)
        } catch (e: UnsupportedJwtException) {
            log.error("FilterException throw UnsupportedJwtException Exception : {}", e.message)
            request.setAttribute("exception", ErrorCode.TOKEN_UNSUPPORTED_ERROR)
            filterChain.doFilter(request, response)
        } catch (e: JwtException) {
            log.error("FilterException throw JwtException Exception : {}", e.message)
            request.setAttribute("exception", ErrorCode.TOKEN_UNKNOWN_ERROR)
            filterChain.doFilter(request, response)
        } catch (e: CommonException) {
            log.error("FilterException throw Exception Exception : {}", e.message)
            request.setAttribute("exception", ErrorCode.NOT_FOUND_USER)
            filterChain.doFilter(request, response)
        } catch (e: Exception) {
            log.error("FilterException throw Exception Exception : {}", e.message)
            request.setAttribute("exception", ErrorCode.UNKNOWN_ERROR)
            filterChain.doFilter(request, response)
        }
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        return true
//        return Arrays.stream(Constants.NO_NEED_AUTH_URLS).anyMatch { url -> url.equals(request.servletPath) };
    }
}
package org.dongguk.lavieenrosehotel.security.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.dongguk.lavieenrosehotel.constraint.Constants
import org.dongguk.lavieenrosehotel.security.service.CustomUserDetailService
import org.dongguk.lavieenrosehotel.utility.HeaderUtil
import org.dongguk.lavieenrosehotel.utility.JwtUtil
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.web.filter.OncePerRequestFilter
import java.util.*


class JwtAuthenticationFilter(
    private val jwtUtil: JwtUtil,
    private val customUserDetailService: CustomUserDetailService,
): OncePerRequestFilter() {
    private val logger by lazy { LoggerFactory.getLogger(JwtAuthenticationFilter::class.java) }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        jwtUtil.validateToken(HeaderUtil.refineAuthenticationHeader(request, "Bearer ")).let { claims ->
            customUserDetailService.loadUserById(claims[Constants.USER_ID_CLAIM_NAME].toString().toLong()).let {
                UsernamePasswordAuthenticationToken(
                    it,
                    null,
                    it.authorities
                ).apply {
                    this.details = WebAuthenticationDetailsSource().buildDetails(request)
                }
            }
        }.let {
            SecurityContextHolder.getContext().authentication = it
        }

        filterChain.doFilter(request, response)
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        return true
//        return Arrays.stream(Constants.NO_NEED_AUTH_URLS).anyMatch { url -> url.equals(request.servletPath) };
    }
}
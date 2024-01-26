package org.dongguk.lavieenrosehotel.security.handler

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.dongguk.lavieenrosehotel.domain.User
import org.dongguk.lavieenrosehotel.dto.type.factory.EProviderFactory
import org.dongguk.lavieenrosehotel.dto.type.factory.ERoleFactory
import org.dongguk.lavieenrosehotel.exception.CommonException
import org.dongguk.lavieenrosehotel.exception.ErrorCode
import org.dongguk.lavieenrosehotel.repository.UserRepository
import org.dongguk.lavieenrosehotel.security.info.UserPrincipal
import org.dongguk.lavieenrosehotel.security.info.factory.OAuth2UserInfoFactory
import org.dongguk.lavieenrosehotel.utility.CookieUtil
import org.dongguk.lavieenrosehotel.utility.JwtUtil
import org.dongguk.lavieenrosehotel.utility.PasswordUtil
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.*


@Component
class OAuth2LoginSuccessHandler(
    @Value("\${server.http-address}")
    private val httpAddress: String,
    @Value("\${server.https-address}")
    private val httpsAddress: String,

    private val jwtUtil: JwtUtil,
    private val userRepository: UserRepository,
): AuthenticationSuccessHandler {
    @Transactional
    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication,
    ) {
        val userPrincipal: UserPrincipal = authentication.principal as UserPrincipal

        val jwtTokens = jwtUtil.generateJwtTokens(
            userPrincipal.id,
            userPrincipal.role,
        )

        userRepository.updateRefreshTokenAndLoginStatus(userPrincipal.id, jwtTokens.refreshToken, true)

        CookieUtil.addCookie(response, "accessToken", jwtTokens.accessToken)
        CookieUtil.addSecureCookie(response, "refreshToken", jwtTokens.refreshToken, jwtUtil.refreshTokenExpiration)

        if (userPrincipal.role == ERoleFactory.of("GUEST")) {
            response.run { sendRedirect("$httpAddress/signup") }
        } else {
            response.run { sendRedirect("$httpAddress/") }
        }


    }
}
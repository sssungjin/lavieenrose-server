package org.dongguk.lavieenrosehotel.controller

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.Valid
import org.dongguk.lavieenrosehotel.annotation.UserId
import org.dongguk.lavieenrosehotel.dto.common.ResponseDto
import org.dongguk.lavieenrosehotel.dto.request.AuthSignUpDto
import org.dongguk.lavieenrosehotel.dto.request.OAuth2SignUpDto
import org.dongguk.lavieenrosehotel.exception.CommonException
import org.dongguk.lavieenrosehotel.exception.ErrorCode
import org.dongguk.lavieenrosehotel.service.AuthService
import org.dongguk.lavieenrosehotel.utility.CookieUtil
import org.dongguk.lavieenrosehotel.utility.JwtUtil
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController(
    private val jwtUtil: JwtUtil,
    private val authService: AuthService
) {
    @PostMapping("/auth/sign-up")
    fun authSignUp(
        @RequestBody @Valid authSignUpDto: AuthSignUpDto,
    ): ResponseDto<*> {
         authService.authSignUp(authSignUpDto)

        return ResponseDto.created(null)
    }

    @PostMapping("/oauth2/sign-up")
    fun oauth2SignUp(
        @UserId userId: Long,
        @RequestBody @Valid oAuth2SignUpDto: OAuth2SignUpDto
    ): ResponseDto<*> {
        authService.oAuth2SignUp(userId, oAuth2SignUpDto)

        return ResponseDto.created(null)
    }

    @PatchMapping("/auth/reissue")
    fun reissue(
        request: HttpServletRequest,
        response: HttpServletResponse, ): ResponseDto<*> {
//        val cookies = request.cookies ?: throw CommonException(ErrorCode.MISSING_REQUEST_COOKIE)
//
//        val cookie = cookies.firstOrNull { cookie -> "refreshToken" == cookie.name }
//            ?: throw CommonException(ErrorCode.MISSING_REQUEST_COOKIE)
//
//        // 토큰 재발급 로직
//        // val jwtTokenDto = authService.reissueJwt(cookie.value)
//
//        CookieUtil.addSecureCookie(response, "refreshToken", cookie.value, jwtUtil.refreshTokenExpiration)

        val result: MutableMap<String, String> = HashMap()

        return ResponseDto.ok(result)
    }

    @PatchMapping("/auth/sign-out")
    fun signOut(
//        @UserId userId: Long,
        request: HttpServletRequest,
        response: HttpServletResponse, ): ResponseDto<*> {
        // 로그아웃 로직
        // authService.signOut(userId)

//        CookieUtil.deleteCookie(request, response, "refreshToken")

        return ResponseDto.ok(null)
    }

}
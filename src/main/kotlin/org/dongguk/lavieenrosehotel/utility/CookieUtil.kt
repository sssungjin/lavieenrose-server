package org.dongguk.lavieenrosehotel.utility

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jakarta.servlet.http.Cookie;
import org.springframework.util.SerializationUtils
import java.util.*


object CookieUtil {
    fun getCookie(request: HttpServletRequest, name: String): Optional<Cookie> {
        return request.cookies?.let { it ->
            it.firstOrNull { cookie -> name == cookie.name }
                ?.let {findCookie -> Optional.of(findCookie) }
        } ?: Optional.empty()
    }

    fun addCookie(response: HttpServletResponse, name: String, value: String) {
        val cookie = Cookie(name, value)
        cookie.path = "/"
        response.addCookie(cookie)
    }

    fun addSecureCookie(response: HttpServletResponse, name: String, value: String, maxAge: Int) {
        val cookie = Cookie(name, value)
        cookie.path = "/"
        cookie.isHttpOnly = true
        cookie.secure = true
        cookie.maxAge = maxAge
        response.addCookie(cookie)
    }

    fun deleteCookie(request: HttpServletRequest, response: HttpServletResponse, name: String) {
        request.cookies?.let { cookies ->
            cookies.firstOrNull { cookie -> name == cookie.name }
                ?.let { findCookie ->
                    findCookie.value = ""
                    findCookie.path = "/"
                    findCookie.maxAge = 0
                    response.addCookie(findCookie)
                }
        }
    }
}
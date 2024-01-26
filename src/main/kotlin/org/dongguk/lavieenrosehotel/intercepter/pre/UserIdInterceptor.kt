package org.dongguk.lavieenrosehotel.intercepter.pre

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.servlet.HandlerInterceptor

class UserIdInterceptor : HandlerInterceptor {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any) : Boolean {
        val authentication : Authentication? = SecurityContextHolder.getContext().authentication;

        if (authentication != null) {
            request.setAttribute("userId", authentication.name);
        }

        return super.preHandle(request, response, handler)
    }
}
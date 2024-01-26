package org.dongguk.lavieenrosehotel.intercepter.pre

import org.dongguk.lavieenrosehotel.annotation.UserId
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

@Component
class UserIdArgumentResolver : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return (parameter.parameterType == Long::class.java
                && parameter.hasParameterAnnotation(UserId::class.java))
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Any? {
        val userId : String? = webRequest.getAttribute("userId", NativeWebRequest.SCOPE_REQUEST) as String?

        return userId?.toLong()
    }
}
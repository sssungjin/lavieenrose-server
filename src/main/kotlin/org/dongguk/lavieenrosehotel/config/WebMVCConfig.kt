package org.dongguk.lavieenrosehotel.config

import org.dongguk.lavieenrosehotel.intercepter.pre.UserIdArgumentResolver
import org.dongguk.lavieenrosehotel.intercepter.pre.UserIdInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@EnableWebMvc
class WebMVCConfig(
    private val userIdArgumentResolver: UserIdArgumentResolver
) : WebMvcConfigurer {

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        super.addArgumentResolvers(resolvers);
        resolvers.add(userIdArgumentResolver);
    }
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(UserIdInterceptor())
            .addPathPatterns("/**")
    }
}
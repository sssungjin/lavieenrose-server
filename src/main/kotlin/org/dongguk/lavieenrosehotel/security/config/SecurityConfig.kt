package org.dongguk.lavieenrosehotel.security.config

import org.dongguk.lavieenrosehotel.constraint.Constants
import org.dongguk.lavieenrosehotel.security.filter.JwtAuthenticationFilter
import org.dongguk.lavieenrosehotel.security.filter.JwtExceptionFilter
import org.dongguk.lavieenrosehotel.security.handler.*
import org.dongguk.lavieenrosehotel.security.service.CustomOAuth2UserService
import org.dongguk.lavieenrosehotel.security.service.CustomUserDetailService
import org.dongguk.lavieenrosehotel.utility.JwtUtil
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtUtil: JwtUtil,
    private val defaultSuccessHandler: DefaultSuccessHandler,
    private val defaultFailureHandler: DefaultFailureHandler,
    private val customUserDetailService: CustomUserDetailService,

    private val oAuth2LoginSuccessHandler: OAuth2LoginSuccessHandler,
    private val oAuth2LoginFailureHandler: OAuth2LoginFailureHandler,
    private val customOAuth2UserService: CustomOAuth2UserService,

    private val jwtAuthEntryPoint: JwtAuthEntryPoint,
    private val jwtAccessDeniedHandler: JwtAccessDeniedHandler,
) {
    @Bean
    protected fun securityFilterChain(httpSecurity: HttpSecurity) : SecurityFilterChain {
        return httpSecurity
            .cors { cors -> cors.configure(httpSecurity) }
            .csrf { obj: AbstractHttpConfigurer<*, *> -> obj.disable() }
            .httpBasic { obj: AbstractHttpConfigurer<*, *> -> obj.disable() }
            .sessionManagement { sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }

            .authorizeHttpRequests { authorize ->
//                authorize
//                    .requestMatchers(*Constants.NO_NEED_AUTH_URLS).permitAll()
//                    .requestMatchers(*Constants.ALL_URLS).hasAnyRole("USER", "ADMIN", "GUEST")
//                    .requestMatchers(*Constants.GUEST_URLS).hasAnyRole("GUEST")
//                    .requestMatchers(*Constants.ADMIN_URLS).hasRole("ADMIN")
//                    .requestMatchers(*Constants.USER_URLS).hasAnyRole("USER", "ADMIN")
//                    .anyRequest().authenticated()
                authorize.anyRequest().permitAll()
            }

            .formLogin {configurer ->
                configurer
                    .loginPage("/login")
                    .loginProcessingUrl("/auth/sign-in")
                    .usernameParameter("serial_id")
                    .passwordParameter("password")
                    .successHandler( defaultSuccessHandler )
                    .failureHandler( defaultFailureHandler )
            }

            .oauth2Login { configurer ->
                configurer
                    .successHandler( oAuth2LoginSuccessHandler )
                    .failureHandler( oAuth2LoginFailureHandler )
                    .userInfoEndpoint {
                        it.userService( customOAuth2UserService )
                    }
            }

            .exceptionHandling { handling ->
                handling
                    .authenticationEntryPoint( jwtAuthEntryPoint )
                    .accessDeniedHandler( jwtAccessDeniedHandler )
            }

            .addFilterBefore(
                JwtAuthenticationFilter(jwtUtil = jwtUtil, customUserDetailService = customUserDetailService),
                UsernamePasswordAuthenticationFilter::class.java)
            .addFilterBefore(
                JwtExceptionFilter(),
                JwtAuthenticationFilter::class.java)

            .build()
    }
}
package org.dongguk.lavieenrosehotel.security.service

import org.dongguk.lavieenrosehotel.dto.type.factory.EProviderFactory
import org.dongguk.lavieenrosehotel.exception.CommonException
import org.dongguk.lavieenrosehotel.exception.ErrorCode
import org.dongguk.lavieenrosehotel.repository.UserRepository
import org.dongguk.lavieenrosehotel.security.info.UserPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class CustomUserDetailService(
    private val userRepository: UserRepository,
): UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails {
        val user = userRepository
            .findFormBySerialIdAndProvider(
                username ?: throw CommonException(ErrorCode.MISSING_REQUEST_PARAMETER),
                EProviderFactory.of("DEFAULT"))
            .orElseThrow { throw CommonException(ErrorCode.NOT_FOUND_USER) }

        return UserPrincipal.create(user, mutableMapOf())
    }

    fun loadUserById(id: Long): UserDetails {
        val user = userRepository
            .findFormById(id)
            .orElseThrow { throw CommonException(ErrorCode.NOT_FOUND_USER) }

        return UserPrincipal.create(user, mutableMapOf())
    }
}
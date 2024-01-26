package org.dongguk.lavieenrosehotel.service

import org.dongguk.lavieenrosehotel.domain.User
import org.dongguk.lavieenrosehotel.dto.request.AuthSignUpDto
import org.dongguk.lavieenrosehotel.dto.request.OAuth2SignUpDto
import org.dongguk.lavieenrosehotel.exception.CommonException
import org.dongguk.lavieenrosehotel.exception.ErrorCode
import org.dongguk.lavieenrosehotel.repository.UserRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder,
) {
    fun authSignUp(authSignUpDto: AuthSignUpDto): Unit {
        userRepository.findBy_serialId(authSignUpDto.serialId)
            .ifPresent { throw CommonException(ErrorCode.DUPLICATED_SERIAL_ID) }

        userRepository.save(
            User.fromSignUpJson(
                authSignUpDto,
                bCryptPasswordEncoder.encode(authSignUpDto.password)
            )
        )
    }

    @Transactional
    fun oAuth2SignUp(userId: Long, oAuth2SignUpDto: OAuth2SignUpDto): Unit {
        userRepository.findById(userId)
            .orElseThrow { CommonException(ErrorCode.NOT_FOUND_USER) }
            .register(oAuth2SignUpDto.name, oAuth2SignUpDto.phoneNumber)
    }
}
package org.dongguk.lavieenrosehotel.service

import org.dongguk.lavieenrosehotel.dto.request.OAuth2SignUpDto
import org.dongguk.lavieenrosehotel.dto.response.UserDetailDto
import org.dongguk.lavieenrosehotel.exception.CommonException
import org.dongguk.lavieenrosehotel.exception.ErrorCode
import org.dongguk.lavieenrosehotel.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userRepository: UserRepository,
) {
    fun readUser(userId: Long): UserDetailDto {
        val user = userRepository.findById(userId).orElseThrow { CommonException(ErrorCode.NOT_FOUND_USER) }

        return UserDetailDto.fromEntity(user)
    }

    @Transactional
    fun updateUser(userId: Long, requestDto: OAuth2SignUpDto) {
        val user = userRepository.findById(userId).orElseThrow { CommonException(ErrorCode.NOT_FOUND_USER) }

        user.register(requestDto.name, requestDto.phoneNumber)
    }
}
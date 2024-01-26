package org.dongguk.lavieenrosehotel.controller

import org.dongguk.lavieenrosehotel.annotation.UserId
import org.dongguk.lavieenrosehotel.dto.common.ResponseDto
import org.dongguk.lavieenrosehotel.dto.request.OAuth2SignUpDto
import org.dongguk.lavieenrosehotel.dto.response.UserDetailDto
import org.dongguk.lavieenrosehotel.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService,
) {
    @GetMapping("")
    fun readUser(@UserId userId: Long): ResponseDto<UserDetailDto> {
        return ResponseDto.ok(userService.readUser(userId))
    }
}
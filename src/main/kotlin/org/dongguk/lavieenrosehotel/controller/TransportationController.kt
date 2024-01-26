package org.dongguk.lavieenrosehotel.controller

import org.dongguk.lavieenrosehotel.dto.common.ResponseDto
import org.dongguk.lavieenrosehotel.dto.response.TransportationListDto
import org.dongguk.lavieenrosehotel.dto.type.ETransportationType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/transportations")
class TransportationController {
    @GetMapping("")
    fun readTransportations(): ResponseDto<*> {
        return ResponseDto.ok(
            ETransportationType.values()
            .map { TransportationListDto.of(it) }
            .toList()
        )
    }
}
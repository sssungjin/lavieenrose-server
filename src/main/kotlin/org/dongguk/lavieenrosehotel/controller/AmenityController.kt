package org.dongguk.lavieenrosehotel.controller

import jakarta.validation.Valid
import org.dongguk.lavieenrosehotel.dto.common.ResponseDto
import org.dongguk.lavieenrosehotel.dto.request.AmenityDto
import org.dongguk.lavieenrosehotel.exception.CommonException
import org.dongguk.lavieenrosehotel.exception.ErrorCode
import org.dongguk.lavieenrosehotel.service.AmenityService
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
class AmenityController(
    private val amenityService: AmenityService,
) {
    @PostMapping("/admin/amenities")
        fun createAmenity(
        @RequestPart(value = "message") @Valid message: AmenityDto,
        @RequestPart(value = "images", required = false) files: List<MultipartFile>?,
    ) = ResponseDto.created(amenityService.createAmenity(message, files))

    @GetMapping("/admin/amenities")
    fun readAmenities(
        @RequestParam("page") page: Int,
        @RequestParam("size") size: Int,
    ): ResponseDto<*> {
        if (page < 1 || size < 1) {
            throw CommonException(ErrorCode.BAD_REQUEST)
        }

        return ResponseDto.ok(amenityService.readAmenities(page, size))
    }

    @GetMapping("/admin/amenities/{amenityId}")
    fun readAmenities(
        @PathVariable("amenityId") amenityId: Long,
    ) = ResponseDto.ok(amenityService.readAmenity(amenityId))

    @PutMapping("/admin/amenities/{amenityId}")
    fun updateAmenity(
        @PathVariable("amenityId") amenityId: Long,
        @RequestBody @Valid requestBody: AmenityDto,
    ) = ResponseDto.ok(amenityService.updateAmenity(amenityId, requestBody))

    @DeleteMapping("/admin/amenities/{amenityId}")
    fun deleteAmenity(
        @PathVariable("amenityId") amenityId: Long,
    ) = ResponseDto.ok(amenityService.deleteAmenity(amenityId))
}
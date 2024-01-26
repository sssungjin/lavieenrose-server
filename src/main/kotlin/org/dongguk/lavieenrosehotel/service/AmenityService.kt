package org.dongguk.lavieenrosehotel.service

import org.dongguk.lavieenrosehotel.domain.Amenity
import org.dongguk.lavieenrosehotel.domain.Image
import org.dongguk.lavieenrosehotel.dto.common.PageInfo
import org.dongguk.lavieenrosehotel.dto.request.AmenityDto
import org.dongguk.lavieenrosehotel.dto.response.AmenityDetailDto
import org.dongguk.lavieenrosehotel.dto.response.AmenityListDto
import org.dongguk.lavieenrosehotel.exception.CommonException
import org.dongguk.lavieenrosehotel.exception.ErrorCode
import org.dongguk.lavieenrosehotel.repository.AmenityRepository
import org.dongguk.lavieenrosehotel.repository.ImageRepository
import org.dongguk.lavieenrosehotel.utility.ImageUtil
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
class AmenityService(
    private val imageUtil: ImageUtil,
    private val amenityRepository: AmenityRepository,
    private val imageRepository: ImageRepository,
) {
    @Transactional
    fun createAmenity(requestBody: AmenityDto, files: List<MultipartFile>?): AmenityDetailDto {
        val amenity = amenityRepository.saveAndFlush(Amenity.fromJson(requestBody))

        val images = if (files?.isNotEmpty() == true) {
            imageUtil.uploadImageFiles(files)
        } else {
            listOf()
        }.map {
            Image(_url = it, _amenity = amenity)
        }

        imageRepository.saveAll(images)

        return AmenityDetailDto.fromEntity(amenity, images)
    }

    fun readAmenities(page: Int, size: Int): Map<String, Any> {
        val pages: Page<Amenity> = Pageable.ofSize(size).withPage(page - 1).let {
                amenityRepository.findAll(it)
            }

        return mapOf(
            "pageInfo" to PageInfo.of(pages),
            "amenities" to pages.content.map { AmenityListDto.fromEntity(it) })
    }

    fun readAmenity(amenityId: Long): AmenityDetailDto {
        val amenity: Amenity = amenityRepository.findById(amenityId)
            .orElseThrow { CommonException(ErrorCode.NOT_FOUND) }

        return AmenityDetailDto.fromEntity(amenity)
    }

    @Transactional
    fun updateAmenity(amenityId: Long, requestBody: AmenityDto) {
        val amenity: Amenity = amenityRepository.findById(amenityId)
            .orElseThrow { CommonException(ErrorCode.NOT_FOUND) }

        amenity.update(requestBody)
    }

    @Transactional
    fun deleteAmenity(amenityId: Long) {
        val amenity: Amenity = amenityRepository.findById(amenityId)
            .orElseThrow { CommonException(ErrorCode.NOT_FOUND) }

        amenityRepository.delete(amenity)
    }
}
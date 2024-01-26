package org.dongguk.lavieenrosehotel.service

import org.dongguk.lavieenrosehotel.domain.Category
import org.dongguk.lavieenrosehotel.domain.Image
import org.dongguk.lavieenrosehotel.dto.common.PageInfo
import org.dongguk.lavieenrosehotel.dto.request.CategoryDto
import org.dongguk.lavieenrosehotel.dto.request.StatusDto
import org.dongguk.lavieenrosehotel.dto.response.CategoryDetailDto
import org.dongguk.lavieenrosehotel.dto.response.CategorySearchDto
import org.dongguk.lavieenrosehotel.dto.type.EVisibleStatus
import org.dongguk.lavieenrosehotel.exception.CommonException
import org.dongguk.lavieenrosehotel.exception.ErrorCode
import org.dongguk.lavieenrosehotel.repository.CategoryRepository
import org.dongguk.lavieenrosehotel.repository.ImageRepository
import org.dongguk.lavieenrosehotel.utility.ImageUtil
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDate

@Service
class CategoryService(
    private val imageUtil: ImageUtil,

    private val categoryRepository: CategoryRepository,
    private val imageRepository: ImageRepository,
) {
    @Transactional
    fun createCategory(requestBody: CategoryDto, files: List<MultipartFile>?): CategoryDetailDto {
        val category =  categoryRepository.saveAndFlush(
            Category.fromJson(requestBody)
        )

        val images = if (files?.isNotEmpty() == true) {
            imageUtil.uploadImageFiles(files)
        } else {
            listOf()
        }.map {
            Image(_url = it, _category = category)
        }

        imageRepository.saveAll(images)

        return CategoryDetailDto.fromEntity(category, images)
    }

    fun readCategories(
        page: Int,
        size: Int,
    ): Map<String, Any> {
        val pageable: Pageable = PageRequest.of(page - 1, size, Sort.by("id"))

        return categoryRepository.findAll(pageable).let { pages ->
            mapOf(
                "page_info" to PageInfo.of(pages),
                "categories" to pages.content.map { CategoryDetailDto.fromEntity(it, it.image) }
            )
        }
    }

    fun readCategory(categoryId: Long): CategoryDetailDto {
        val category = categoryRepository.findById(categoryId)
            .orElseGet { throw CommonException(ErrorCode.NOT_FOUND) }
        return CategoryDetailDto.fromEntity(
            category, category.image
        )
    }

    @Transactional
    fun updateCategoryStatus(categoryId: Long, requestBody: StatusDto) {
        val category = categoryRepository.findById(categoryId)
            .orElseGet { throw CommonException(ErrorCode.NOT_FOUND) }

        category.updateStatus(requestBody)
    }

    @Transactional
    fun updateCategory(categoryId: Long, requestBody: CategoryDto) {
        val category = categoryRepository.findById(categoryId)
            .orElseGet { throw CommonException(ErrorCode.NOT_FOUND) }

        category.update(requestBody)
    }

    @Transactional
    fun deleteCategory(categoryId: Long) {
        val category = categoryRepository.findById(categoryId)
            .orElseGet { throw CommonException(ErrorCode.NOT_FOUND) }

        categoryRepository.delete(category)
    }

    fun readAvailableCategories(
        startDate: LocalDate,
        endDate: LocalDate,
        totalCnt: Int): List<CategorySearchDto> {

        val categories = categoryRepository.findCategoriesByStatus(
            status = EVisibleStatus.VISIBLE,
            totalCnt = totalCnt
        )

        return categories.map {
            CategorySearchDto.fromEntity(it)
        }
    }
}

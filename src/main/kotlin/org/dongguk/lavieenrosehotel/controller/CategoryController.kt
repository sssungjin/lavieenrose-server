package org.dongguk.lavieenrosehotel.controller

import jakarta.validation.Valid
import org.dongguk.lavieenrosehotel.annotation.Date
import org.dongguk.lavieenrosehotel.dto.common.ResponseDto
import org.dongguk.lavieenrosehotel.dto.request.CategoryDto
import org.dongguk.lavieenrosehotel.dto.request.StatusDto
import org.dongguk.lavieenrosehotel.exception.CommonException
import org.dongguk.lavieenrosehotel.exception.ErrorCode
import org.dongguk.lavieenrosehotel.service.CategoryService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDate

@RestController
class CategoryController(
    private val categoryService: CategoryService,
) {
    @GetMapping("/categories")
    fun readCategories(
        @RequestParam("startDate") @Date startDate: LocalDate,
        @RequestParam("endDate") @Date endDate: LocalDate,
        @RequestParam("adultCnt") adultCnt: Int,
        @RequestParam("teenagerCnt") teenagerCnt: Int,
        @RequestParam("childCnt") childCnt: Int,
    ): ResponseDto<*> {
        if (adultCnt < 0 || teenagerCnt < 0 || childCnt < 0
            || (adultCnt + teenagerCnt + childCnt < 1)
            || startDate.isAfter(endDate)) {
            throw CommonException(ErrorCode.INVALID_ARGUMENT)
        }
        return ResponseDto.ok(
            categoryService.readAvailableCategories(
                startDate,
                endDate,
                adultCnt + teenagerCnt
            )
        )
    }

    @PostMapping("/admin/categories")
    fun createCategory(
        @RequestPart(value = "message") @Valid requestBody: CategoryDto,
        @RequestPart(value = "images", required = false) files: List<MultipartFile>?,
    ) = ResponseDto.created(categoryService.createCategory(requestBody, files))

    @GetMapping("/admin/categories")
    fun readCategories(
        @RequestParam("page") page: Int,
        @RequestParam("size") size: Int,
    ): ResponseDto<*> {
        if (page < 1 || size < 1) {
            throw CommonException(ErrorCode.INVALID_ARGUMENT)
        }
        return ResponseDto.ok(categoryService.readCategories(page, size))
    }

    @GetMapping("/admin/categories/{categoryId}")
    fun readCategory(
        @PathVariable categoryId: Long,
    ) = ResponseDto.ok(categoryService.readCategory(categoryId))

    @PatchMapping("/admin/categories/{categoryId}")
    fun updateCategoryStatus(
        @PathVariable categoryId: Long,
        @RequestBody requestBody: StatusDto,
    ) = ResponseDto.ok(categoryService.updateCategoryStatus(categoryId, requestBody))

    @PutMapping("/admin/categories/{categoryId}")
    fun updateCategory(
        @PathVariable categoryId: Long,
        @RequestBody requestBody: CategoryDto,
    ) = ResponseDto.ok(categoryService.updateCategory(categoryId, requestBody))

    @DeleteMapping("/admin/categories/{categoryId}")
    fun deleteCategory(
        @PathVariable categoryId: Long,
    ) = ResponseDto.ok(categoryService.deleteCategory(categoryId))
}
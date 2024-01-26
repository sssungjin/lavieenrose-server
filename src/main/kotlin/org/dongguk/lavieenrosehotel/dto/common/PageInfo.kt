package org.dongguk.lavieenrosehotel.dto.common

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.domain.Page

class PageInfo(
    @JsonProperty("total_page") val totalPage: Int,
    @JsonProperty("current_page") val currentPage: Int,
    @JsonProperty("total_element") val totalElement: Long,
    @JsonProperty("current_element") val currentElement: Int
) {
    companion object {
        fun of(page: Page<*>): PageInfo {
            return PageInfo(
                totalPage = page.totalPages,
                currentPage = page.number + 1,
                totalElement = page.totalElements,
                currentElement = page.numberOfElements,
            )
        }
    }
}
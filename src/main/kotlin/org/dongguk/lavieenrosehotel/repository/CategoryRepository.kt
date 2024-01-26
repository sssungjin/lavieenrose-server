package org.dongguk.lavieenrosehotel.repository

import org.dongguk.lavieenrosehotel.domain.Category
import org.dongguk.lavieenrosehotel.dto.type.EVisibleStatus
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CategoryRepository: JpaRepository<Category, Long> {

    @EntityGraph(attributePaths = ["_image", "_rooms", "_reservationRooms"])
    override fun findById(id: Long): Optional<Category>

    @EntityGraph(attributePaths = ["_image"])
    override fun findAll(pageable: Pageable): Page<Category>

    @Query("SELECT c FROM Category c " +
            "WHERE c._id = :id AND c._status = :status")
    fun findCategoryByIdAndStatus(id: Long, status: EVisibleStatus): Optional<Category>

    @Query("SELECT c FROM Category c WHERE c._status = :status AND c._maxCapacity >= :totalCnt")
    fun findCategoriesByStatus(status: EVisibleStatus, totalCnt: Int): List<Category>
}
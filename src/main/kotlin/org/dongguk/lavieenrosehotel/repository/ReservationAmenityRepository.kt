package org.dongguk.lavieenrosehotel.repository

import org.dongguk.lavieenrosehotel.domain.ReservationAmenity
import org.dongguk.lavieenrosehotel.domain.User
import org.dongguk.lavieenrosehotel.dto.type.EAmenityType
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ReservationAmenityRepository: JpaRepository<ReservationAmenity, Long> {
    @EntityGraph(attributePaths = ["_paymentHistories"])
    fun findAllBy_user(user: User, pageable: Pageable): Page<ReservationAmenity>

    @EntityGraph(attributePaths = ["_paymentHistories", "_amenity"])
    @Query("SELECT ra FROM ReservationAmenity ra JOIN ra._amenity a WHERE a._type = :type")
    fun findAllByType(type: EAmenityType, pageable: Pageable): Page<ReservationAmenity>

}
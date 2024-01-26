package org.dongguk.lavieenrosehotel.repository

import org.dongguk.lavieenrosehotel.domain.ReservationTransportation
import org.dongguk.lavieenrosehotel.domain.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ReservationTransportationRepository: JpaRepository<ReservationTransportation, Long> {
    @EntityGraph(attributePaths = ["_paymentHistories"])
    fun findAllBy_user(user: User, pageable: Pageable): Page<ReservationTransportation>
}
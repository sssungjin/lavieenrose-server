package org.dongguk.lavieenrosehotel.repository

import org.dongguk.lavieenrosehotel.domain.ReservationAmenity
import org.dongguk.lavieenrosehotel.domain.ReservationRoom
import org.dongguk.lavieenrosehotel.domain.ReservationTransportation
import org.dongguk.lavieenrosehotel.domain.User
import org.dongguk.lavieenrosehotel.dto.type.EAmenityType
import org.dongguk.lavieenrosehotel.dto.type.ERoomType
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.Optional

interface ReservationRoomRepository: JpaRepository<ReservationRoom, Long> {

    @EntityGraph(attributePaths = ["_user"])
    override fun findById(id: Long): Optional<ReservationRoom>

    @EntityGraph(attributePaths = ["_paymentHistories"])
    fun findAllBy_user(user: User, pageable: Pageable): Page<ReservationRoom>

    @EntityGraph(attributePaths = ["_paymentHistories"])
    @Query("SELECT rr FROM ReservationRoom rr JOIN rr._room r JOIN r._category c WHERE c._roomType = :type")
    fun findAllByCategoryRoomType(type: ERoomType, pageable: Pageable): Page<ReservationRoom>

}
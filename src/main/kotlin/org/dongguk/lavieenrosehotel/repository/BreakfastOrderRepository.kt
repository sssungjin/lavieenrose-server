package org.dongguk.lavieenrosehotel.repository

import org.dongguk.lavieenrosehotel.domain.BreakfastOrder
import org.dongguk.lavieenrosehotel.domain.ReservationRoom
import org.springframework.data.jpa.repository.JpaRepository

interface BreakfastOrderRepository: JpaRepository<BreakfastOrder, Long> {

    fun deleteAllBy_reservationRoom(reservationRoom: ReservationRoom): List<BreakfastOrder>

}
package org.dongguk.lavieenrosehotel.repository

import org.dongguk.lavieenrosehotel.domain.Reservation
import org.springframework.data.jpa.repository.JpaRepository

interface ReservationRepository : JpaRepository<Reservation, Long> {
}
package org.dongguk.lavieenrosehotel.repository

import org.dongguk.lavieenrosehotel.domain.RoomOption
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RoomOptionRepository: JpaRepository<RoomOption, Long> {
}
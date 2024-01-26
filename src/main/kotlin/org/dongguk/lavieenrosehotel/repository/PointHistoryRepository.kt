package org.dongguk.lavieenrosehotel.repository

import org.dongguk.lavieenrosehotel.domain.PointHistory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PointHistoryRepository: JpaRepository<PointHistory, Long> {
}
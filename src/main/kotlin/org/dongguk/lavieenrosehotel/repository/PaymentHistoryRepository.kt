package org.dongguk.lavieenrosehotel.repository

import org.dongguk.lavieenrosehotel.domain.PaymentHistory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PaymentHistoryRepository: JpaRepository<PaymentHistory, Long> {
}
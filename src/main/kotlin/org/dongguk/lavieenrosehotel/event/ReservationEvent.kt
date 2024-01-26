package org.dongguk.lavieenrosehotel.event

import org.dongguk.lavieenrosehotel.dto.type.EPaymentMethod

class ReservationEvent(
    val impUid: String,
    val isRefund: Boolean,
    val userId: Long,
    val reservationId: Long,
    val price: Int,
    val method: EPaymentMethod,
) {
}
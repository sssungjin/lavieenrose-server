package org.dongguk.lavieenrosehotel.event

import org.dongguk.lavieenrosehotel.dto.type.EReservationStatus

class PaymentVerifyEvent(
    val reservationId: Long,
    val status: EReservationStatus,
) {
}
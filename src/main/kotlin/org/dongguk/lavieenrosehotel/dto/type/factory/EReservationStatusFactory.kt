package org.dongguk.lavieenrosehotel.dto.type.factory

import org.dongguk.lavieenrosehotel.dto.type.EReservationStatus
import org.dongguk.lavieenrosehotel.exception.CommonException
import org.dongguk.lavieenrosehotel.exception.ErrorCode

object EReservationStatusFactory {
    fun of(value: String): EReservationStatus {
        return when (value) {
            "RESERVATION" -> EReservationStatus.RESERVATION
            "DEPOSIT_CONFIRMATION" -> EReservationStatus.DEPOSIT_CONFIRMATION
            "CHECK_IN" -> EReservationStatus.CHECK_IN
            "CHECK_OUT" -> EReservationStatus.CHECK_OUT
            "CANCEL" -> EReservationStatus.CANCEL
            else -> throw CommonException(ErrorCode.NOT_FOUND)
        }
    }
}
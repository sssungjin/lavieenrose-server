package org.dongguk.lavieenrosehotel.dto.type.factory

import org.dongguk.lavieenrosehotel.dto.type.EPaymentMethod
import org.dongguk.lavieenrosehotel.exception.CommonException
import org.dongguk.lavieenrosehotel.exception.ErrorCode

object EPaymentMethodFactory {
    fun of(value: String): EPaymentMethod {
        return when (value) {
            "CASH" -> EPaymentMethod.CASH
            "CARD" -> EPaymentMethod.CARD
            "KAKAOPAY" -> EPaymentMethod.KAKAOPAY
            else -> throw CommonException(ErrorCode.NOT_FOUND)
        }
    }
}
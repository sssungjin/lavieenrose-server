package org.dongguk.lavieenrosehotel.dto.type.factory

import org.dongguk.lavieenrosehotel.dto.type.ETransportationType
import org.dongguk.lavieenrosehotel.exception.CommonException
import org.dongguk.lavieenrosehotel.exception.ErrorCode

object ETransportationTypeFactory {
    fun of(id: Int): ETransportationType {
        return when (id) {
            1 -> ETransportationType.GENERAL_BUS
            2 -> ETransportationType.LUXURY_BUS
            3 -> ETransportationType.PREMIUM_BUS
            else -> throw CommonException(ErrorCode.NOT_FOUND)
        }
    }
}
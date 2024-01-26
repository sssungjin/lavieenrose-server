package org.dongguk.lavieenrosehotel.dto.type.factory

import org.dongguk.lavieenrosehotel.dto.type.EViewType
import org.dongguk.lavieenrosehotel.exception.CommonException
import org.dongguk.lavieenrosehotel.exception.ErrorCode

object EViewTypeFactory {
    fun of(value: String): EViewType {
        return when (value) {
            "OCEAN" -> EViewType.OCEAN
            "CITY" -> EViewType.CITY
            else -> throw CommonException(ErrorCode.NOT_FOUND)
        }
    }
}
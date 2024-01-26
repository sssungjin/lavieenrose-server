package org.dongguk.lavieenrosehotel.dto.type.factory

import org.dongguk.lavieenrosehotel.dto.type.EAmenityType
import org.dongguk.lavieenrosehotel.exception.CommonException
import org.dongguk.lavieenrosehotel.exception.ErrorCode

object EAmenityTypeFactory {
    fun of(value: String): EAmenityType {
        return when (value) {
            "SKI" -> EAmenityType.SKI
            "WATER_PARK" -> EAmenityType.WATER_PARK
            else -> throw CommonException(ErrorCode.NOT_FOUND)
        }
    }
}
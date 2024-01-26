package org.dongguk.lavieenrosehotel.dto.type.factory

import org.dongguk.lavieenrosehotel.dto.type.ERoomType
import org.dongguk.lavieenrosehotel.exception.CommonException
import org.dongguk.lavieenrosehotel.exception.ErrorCode

object ERoomTypeFactory {
    fun of(value: String): ERoomType {
        return when (value) {
            "SPA" -> ERoomType.SPA
            "GENERAL" -> ERoomType.GENERAL
            "SEMINAR" -> ERoomType.SEMINAR
            else -> throw CommonException(ErrorCode.NOT_FOUND)
        }
    }
}
package org.dongguk.lavieenrosehotel.dto.type.factory

import org.dongguk.lavieenrosehotel.dto.type.EProvider
import org.dongguk.lavieenrosehotel.exception.CommonException
import org.dongguk.lavieenrosehotel.exception.ErrorCode

object EProviderFactory {
    fun of(value: String): EProvider {
        return when (value) {
            "KAKAO" -> EProvider.KAKAO
            "GOOGLE" -> EProvider.GOOGLE
            "DEFAULT" -> EProvider.DEFAULT
            else -> throw CommonException(ErrorCode.INVALID_PROVIDER)
        }
    }
}
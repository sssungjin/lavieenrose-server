package org.dongguk.lavieenrosehotel.dto.type.factory

import org.dongguk.lavieenrosehotel.dto.type.EVisibleStatus
import org.dongguk.lavieenrosehotel.exception.CommonException
import org.dongguk.lavieenrosehotel.exception.ErrorCode

object ERoomStatusFactory {
    fun of(value: String): EVisibleStatus {
        return when (value) {
            "VISIBLE" -> EVisibleStatus.VISIBLE
            "INVISIBLE" -> EVisibleStatus.INVISIBLE
            else -> throw CommonException(ErrorCode.NOT_FOUND_ROOM_STATUS)
        }
    }
}
package org.dongguk.lavieenrosehotel.dto.response

import org.dongguk.lavieenrosehotel.dto.type.ETransportationType

class TransportationListDto(
    val id: Int,
    val name: String,
    val capacity: Int,
    val price: Int,
) {
    companion object {
        fun of(type: ETransportationType): TransportationListDto {
            return TransportationListDto(type.id, type.koreanName, type.capacity, type.price)
        }
    }
}
package org.dongguk.lavieenrosehotel.dto.type

enum class ETransportationType(val value: String, val koreanName: String, val id: Int, val capacity: Int, val price: Int) {
    GENERAL_BUS("GENERAL", "일반 버스", 1, 45, 800_000),
    LUXURY_BUS("LUXURY", "고급 버스",2, 31, 1_000_000),
    PREMIUM_BUS("PREMIUM", "프리미엄 버스",3, 30, 1_300_000),
}
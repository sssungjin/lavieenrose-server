package org.dongguk.lavieenrosehotel.dto.type

enum class EReservationStatus(private val _value: String) {
    RESERVATION("RESERVATION"),
    DEPOSIT_CONFIRMATION("DEPOSIT_CONFIRMATION"),
    CHECK_IN("CHECK_IN"),
    CHECK_OUT("CHECK_OUT"),
    CANCEL("CANCEL");
}
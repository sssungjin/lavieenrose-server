package org.dongguk.lavieenrosehotel.dto.request

import com.fasterxml.jackson.annotation.JsonProperty
import org.dongguk.lavieenrosehotel.dto.type.EReservationStatus
import org.jetbrains.annotations.NotNull

class ReservationStatusDto (
    @JsonProperty("status") @field:NotNull
    val status: EReservationStatus,
) {
}
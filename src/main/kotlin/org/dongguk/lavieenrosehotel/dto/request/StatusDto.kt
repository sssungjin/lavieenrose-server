package org.dongguk.lavieenrosehotel.dto.request

import com.fasterxml.jackson.annotation.JsonProperty
import org.dongguk.lavieenrosehotel.dto.type.EVisibleStatus
import org.jetbrains.annotations.NotNull

class StatusDto(
    @JsonProperty("status")
    @field:NotNull
    val status: EVisibleStatus,
) {
}
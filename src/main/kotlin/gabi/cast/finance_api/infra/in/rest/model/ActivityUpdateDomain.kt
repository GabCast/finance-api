package gabi.cast.finance_api.infra.`in`.rest.model

import com.fasterxml.jackson.annotation.JsonProperty
import gabi.cast.finance_api.domain.shared.ActivityType
import jakarta.validation.constraints.PositiveOrZero

data class ActivityUpdateDomain (
    @field:PositiveOrZero(message = "amount cannot be negative")
    @JsonProperty("amount")
    val amount: Double? = null,

    @JsonProperty("type")
    val type: ActivityType? = null,

    @JsonProperty("paid")
    val paid: Boolean? = null
)
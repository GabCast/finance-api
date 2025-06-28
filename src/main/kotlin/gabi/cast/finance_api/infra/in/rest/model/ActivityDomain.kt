package gabi.cast.finance_api.infra.`in`.rest.model

import com.fasterxml.jackson.annotation.JsonProperty
import gabi.cast.finance_api.domain.shared.ActivityType
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.PositiveOrZero
import java.util.UUID

data class ActivityDomain(
    @field:NotNull(message = "accountId cannot be null")
    @JsonProperty("accountId")
    val accountId: UUID,

    @field:NotNull(message = "memberId cannot be null")
    @field:PositiveOrZero(message = "amount cannot be negative")
    @JsonProperty("amount")
    val amount: Double,

    @field:NotNull(message = "type cannot be null")
    @JsonProperty("type")
    val type: ActivityType,

    @JsonProperty("paid")
    val paid: Boolean
)
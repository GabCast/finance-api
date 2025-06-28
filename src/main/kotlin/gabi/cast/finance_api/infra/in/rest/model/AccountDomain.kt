package gabi.cast.finance_api.infra.`in`.rest.model

import com.fasterxml.jackson.annotation.JsonProperty
import gabi.cast.finance_api.domain.shared.Currency
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.util.UUID

data class AccountDomain(
    @field:NotNull(message = "memberId cannot be null")
    @JsonProperty("memberId")
    val memberId: UUID,

    @field:NotNull(message = "currency cannot be null")
    @JsonProperty("currency")
    val currency: Currency,

    @field:NotNull(message = "name cannot be null")
    @field:NotBlank(message = "name cannot be blank")
    @JsonProperty("name")
    val name: String,
)

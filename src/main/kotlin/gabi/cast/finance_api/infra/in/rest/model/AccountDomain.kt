package gabi.cast.finance_api.infra.`in`.rest.model

import com.fasterxml.jackson.annotation.JsonProperty
import gabi.cast.finance_api.domain.shared.Currency
import java.util.UUID

data class AccountDomain(
    @JsonProperty("memberId")
    val memberId: UUID,
    @JsonProperty("currency")
    val currency: Currency,
    @JsonProperty("name")
    val name: String,
)

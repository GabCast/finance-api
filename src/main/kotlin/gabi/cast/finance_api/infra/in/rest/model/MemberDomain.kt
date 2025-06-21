package gabi.cast.finance_api.infra.`in`.rest.model

import com.fasterxml.jackson.annotation.JsonProperty

data class MemberDomain(
    @JsonProperty("reference")
    val reference: String,
)

package gabi.cast.finance_api.infra.`in`.rest.model

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank

data class MemberDomain(
    @field:NotBlank(message = "Reference cannot be blank")
    @JsonProperty("reference")
    val reference: String,
)

package gabi.cast.finance_api.infra.`in`.rest.resource

import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

data class ErrorDetails(
    @JsonProperty("code") val code: String,
    @JsonProperty("title") val title: String,
    @JsonProperty("detail") val detail: String,
    @JsonProperty("source") val source: String? = null
) : Serializable
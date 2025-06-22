package gabi.cast.finance_api.infra.`in`.rest.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.UUID

data class CommentDomain(
    @JsonProperty("activityId")
    val activityId: UUID,
    @JsonProperty("text")
    val text: String
)

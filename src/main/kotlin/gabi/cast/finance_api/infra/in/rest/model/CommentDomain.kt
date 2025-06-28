package gabi.cast.finance_api.infra.`in`.rest.model

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.util.UUID

data class CommentDomain(

    @field:NotNull(message = "activityId cannot be null")
    @JsonProperty("activityId")
    val activityId: UUID,

    @field:NotNull(message = "text cannot be null")
    @field:NotBlank(message = "text cannot be blank")
    @JsonProperty("text")
    val text: String
)

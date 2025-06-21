package gabi.cast.finance_api.infra.`in`.rest.resource

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import gabi.cast.finance_api.domain.shared.ErrorResult
import org.springframework.http.HttpStatus
import java.io.Serializable
import java.util.*

sealed class ErrorResponse(
    @get:JsonIgnore open val httpStatusCode: HttpStatus,
    @get:JsonProperty("error_code") open val errorCode: String,
    @get:JsonProperty("message") open val message: String,
    @get:JsonProperty("title") open val title: String,
    @get:JsonProperty("details") open val details: List<ErrorDetails>? = null,
    @get:JsonProperty("trace_id") open var traceId: String = UUID.randomUUID().toString()
) : Serializable {
    companion object {
        fun from(errorResult: ErrorResult): ErrorResponse {
            val errorResponse = when (errorResult) {
                ErrorResult.ConnectionTimeoutError -> ConnectionTimeoutError
                ErrorResult.InternalServerError -> InternalServerError
                ErrorResult.NotFound -> NotFoundError
                ErrorResult.MemberNotFound -> MemberNotFoundError
                ErrorResult.AccountsNotFound -> AccountsNotFoundError
                ErrorResult.ActivitiesForMemberNotFound -> ActivityNotFoundForMemberError
                ErrorResult.ActivitiesForAccountNotFound -> ActivityNotFoundForAccountError
            }
            errorResponse.traceId = UUID.randomUUID().toString()
            return errorResponse
        }
    }


    data object InternalServerError : ErrorResponse(
        HttpStatus.INTERNAL_SERVER_ERROR,
        errorCode = "INTERNAL_SERVER_ERROR",
        title = "Internal server error.",
        message = "An error has occurred."
    )

    data object NotFoundError : ErrorResponse(
        HttpStatus.NO_CONTENT,
        errorCode = "NO CONTENT",
        title = "No entity found for the specific key.",
        message = "We do not found an activity based on the idempotency-key provided."
    )

    data object MemberNotFoundError : ErrorResponse(
        HttpStatus.NOT_FOUND,
        errorCode = "MEMBER NOT FOUND",
        title = "No member found with the id.",
        message = "No member found with the id."
    )

    data object AccountsNotFoundError : ErrorResponse(
        HttpStatus.NOT_FOUND,
        errorCode = "ACCOUNTS NOT FOUND",
        title = "No accounts found for this member.",
        message = "No accounts found for this member."
    )

    data object ActivityNotFoundForAccountError : ErrorResponse(
        HttpStatus.NOT_FOUND,
        errorCode = "ACTIVITY NOT FOUND",
        title = "No activity found for this account.",
        message = "No activity found for this account."
    )

    data object ActivityNotFoundForMemberError : ErrorResponse(
        HttpStatus.NOT_FOUND,
        errorCode = "ACTIVITY NOT FOUND",
        title = "No activity found for this member.",
        message = "No activity found for this member."
    )

    data class BadRequestError(
        override val message: String,
        override val details: List<ErrorDetails>? = null
    ) : ErrorResponse(
        HttpStatus.BAD_REQUEST,
        errorCode = "BAD_REQUEST",
        title = "Bad Request.",
        message = message,
        details = details
    )

    data class SecurityError(
        override val httpStatusCode: HttpStatus,
        override val errorCode: String,
        override val message: String,
        override val title: String,
        @get:JsonProperty("trace_id") override var traceId: String = UUID.randomUUID().toString()
    ) : ErrorResponse(
        httpStatusCode = httpStatusCode,
        errorCode = errorCode,
        message = message,
        title = title
    )

    data object ConnectionTimeoutError : ErrorResponse(
        HttpStatus.GATEWAY_TIMEOUT,
        errorCode = "CONNECTION_TIMEOUT",
        title = "Connection Timeout",
        message = "The server could not establish a connection within the allowed time."
    )

}
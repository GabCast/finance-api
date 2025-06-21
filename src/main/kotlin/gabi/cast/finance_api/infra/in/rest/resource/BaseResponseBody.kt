package gabi.cast.finance_api.infra.`in`.rest.resource

import com.fasterxml.jackson.annotation.JsonProperty
import gabi.cast.finance_api.domain.shared.ActionResult
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.io.Serializable

sealed class BaseResponseBody<out T> : Serializable {
    data class Error(@JsonProperty("error") val errorResponse: ErrorResponse) : BaseResponseBody<Nothing>()

    data class Success<out T>(
        @JsonProperty("data") val data: T? = null
    ) : BaseResponseBody<T>()

    companion object {

        fun <T> from(actionResult: ActionResult): BaseResponseBody<T> =
            when (actionResult) {
                is ActionResult.Error -> {
                    val errorResponse = ErrorResponse.Companion.from(actionResult.error)
                    println("Error occurred with Trace ID: ${errorResponse.traceId}, Code: ${errorResponse.errorCode}, Message: ${errorResponse.message}")
                    Error(errorResponse)
                }

                is ActionResult.Success -> Success(actionResult.result as T)
            }

        fun <D, T> from(
            actionResult: ActionResult,
            transform: (D?) -> T?
        ): BaseResponseBody<T> =
            when (actionResult) {
                is ActionResult.Error -> {
                    val errorResponse = ErrorResponse.Companion.from(actionResult.error)
                    println("Error occurred with Trace ID: ${errorResponse.traceId}, Code: ${errorResponse.errorCode}, Message: ${errorResponse.message}")
                    Error(errorResponse)
                }

                is ActionResult.Success -> Success(transform(actionResult.result as D))
            }
    }

    fun asEntity(successHttpStatus: HttpStatus = HttpStatus.OK): ResponseEntity<*> =
        when (this) {
            is Error -> ResponseEntity.status(this.errorResponse.httpStatusCode).body(this)
            is Success -> ResponseEntity.status(successHttpStatus).body(this)
        }
}
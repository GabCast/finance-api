package gabi.cast.finance_api.domain.shared

import gabi.cast.finance_api.infra.`in`.rest.resource.BaseResponseBody
import gabi.cast.finance_api.infra.`in`.rest.resource.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity


fun <A, B> ActionResult<List<A>>.mapList(
    transform: (A) -> B,
    successStatus: HttpStatus = HttpStatus.OK
): ResponseEntity<*> = when (this) {
    is ActionResult.Error -> {
        val errorResponse = ErrorResponse.from(this.error)
        ResponseEntity.status(errorResponse.httpStatusCode).body(errorResponse)
    }

    is ActionResult.Success -> BaseResponseBody.from<List<A>, List<B>>(this) {
        it?.map(transform)
    }.asEntity(successHttpStatus = successStatus)
}

fun <A, B> ActionResult<A>.map(
    transform: (A) -> B,
    successStatus: HttpStatus = HttpStatus.OK
): ResponseEntity<*> = when (this) {
    is ActionResult.Error -> {
        val errorResponse = ErrorResponse.from(this.error)
        ResponseEntity.status(errorResponse.httpStatusCode).body(errorResponse)
    }

    is ActionResult.Success -> BaseResponseBody.from<A, B>(this) {
        it?.let(transform)
    }.asEntity(successHttpStatus = successStatus)
}

fun ActionResult<Unit>.mapUnit(successStatus: HttpStatus = HttpStatus.OK): ResponseEntity<*> = when (this) {
    is ActionResult.Error -> {
        val errorResponse = ErrorResponse.from(this.error)
        ResponseEntity.status(errorResponse.httpStatusCode).body(errorResponse)
    }

    is ActionResult.Success -> ResponseEntity.status(successStatus).build<Any>()
}
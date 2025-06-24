package gabi.cast.finance_api.domain.shared

import java.util.Optional

fun <T> Optional<T>.toActionResult(notFoundError: ErrorResult): ActionResult<T> {
    return if (this.isPresent) {
        ActionResult.Success(this.get())
    } else {
        ActionResult.Error(notFoundError)
    }
}
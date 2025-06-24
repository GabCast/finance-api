package gabi.cast.finance_api.domain.shared

sealed class ActionResult<out T> {
    data class Success<T>(val result: Any? = null) : ActionResult<T>()
    data class Error(val error: ErrorResult) : ActionResult<Nothing>()
}
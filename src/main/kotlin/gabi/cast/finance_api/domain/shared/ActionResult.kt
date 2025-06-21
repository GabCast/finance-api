package gabi.cast.finance_api.domain.shared

sealed class ActionResult {
    data class Success(val result: Any? = null) : ActionResult()
    data class Error(val error: ErrorResult) : ActionResult()
}
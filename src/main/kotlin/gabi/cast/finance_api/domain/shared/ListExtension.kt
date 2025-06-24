package gabi.cast.finance_api.domain.shared

fun <T>List<T>.toActionResultIfNotEmpty(error: ErrorResult): ActionResult<List<T>> {
    return if (this.isNotEmpty()) {
        ActionResult.Success(this)
    } else {
        ActionResult.Error(error)
    }
}

fun <T> T.toActionResultIf(predicate: (T) -> Boolean, error: ErrorResult): ActionResult<T> {
    return if (predicate(this)) {
        ActionResult.Success(this)
    } else {
        ActionResult.Error(error)
    }
}
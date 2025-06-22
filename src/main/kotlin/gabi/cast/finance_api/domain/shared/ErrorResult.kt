package gabi.cast.finance_api.domain.shared

sealed class ErrorResult {
    data object InternalServerError : ErrorResult()
    data object MemberNotFound : ErrorResult()
    data object AccountsNotFound : ErrorResult()
    data object AccountNotFound : ErrorResult()
    data object ConnectionTimeoutError : ErrorResult()
    data object NotFound : ErrorResult()
    data object ActivitiesForMemberNotFound : ErrorResult()
    data object ActivitiesForAccountNotFound : ErrorResult()
    data object CommentsForActivityNotFound : ErrorResult()
    data object ActivityNotFound : ErrorResult()
}
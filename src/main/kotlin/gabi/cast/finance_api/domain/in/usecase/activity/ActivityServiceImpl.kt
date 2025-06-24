package gabi.cast.finance_api.domain.`in`.usecase.activity

import gabi.cast.finance_api.domain.`in`.ActivityService
import gabi.cast.finance_api.domain.`in`.entity.activity.Activity
import gabi.cast.finance_api.domain.shared.ActionResult
import gabi.cast.finance_api.domain.shared.ErrorResult
import gabi.cast.finance_api.domain.shared.toActionResultIfNotEmpty
import gabi.cast.finance_api.infra.out.repository.ActivityAdapter
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class ActivityServiceImpl(
    private val activityAdapter: ActivityAdapter
) : ActivityService {

    override fun findByAccountMemberId(id: UUID): ActionResult<List<Activity?>> = runCatching {
        activityAdapter.findByAccountMemberId(id)
    }.fold(
        onSuccess = { result ->
            result.toActionResultIfNotEmpty(ErrorResult.ActivitiesForMemberNotFound)
        },
        onFailure = { ActionResult.Error(ErrorResult.InternalServerError) }
    ).also {
        println("Found activity for member id: $id")
        println("Result: $it")
    }

    override fun findByAccountId(id: UUID): ActionResult<List<Activity?>> = runCatching {
        activityAdapter.findByAccountId(id)
    }.fold(
        onSuccess = { result ->
            result.toActionResultIfNotEmpty(ErrorResult.ActivitiesForAccountNotFound)
        },
        onFailure = { ActionResult.Error(ErrorResult.InternalServerError) }
    )

}
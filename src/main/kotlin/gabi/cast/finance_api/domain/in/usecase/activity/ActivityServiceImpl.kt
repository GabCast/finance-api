package gabi.cast.finance_api.domain.`in`.usecase.activity

import gabi.cast.finance_api.domain.`in`.ActivityService
import gabi.cast.finance_api.domain.`in`.entity.activity.Activity
import gabi.cast.finance_api.domain.shared.ActionResult
import gabi.cast.finance_api.domain.shared.ErrorResult
import gabi.cast.finance_api.domain.shared.toActionResultIfNotEmpty
import gabi.cast.finance_api.infra.`in`.rest.model.ActivityDomain
import gabi.cast.finance_api.infra.`in`.rest.model.ActivityUpdateDomain
import gabi.cast.finance_api.infra.out.repository.AccountAdapter
import gabi.cast.finance_api.infra.out.repository.ActivityAdapter
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class ActivityServiceImpl(
    private val activityAdapter: ActivityAdapter,
    private val accountAdapter: AccountAdapter,
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

    override fun save(activity: ActivityDomain): ActionResult<Activity> = runCatching {
        // get account
        val account = accountAdapter.findById(activity.accountId).orElse(null)
            ?: return ActionResult.Error(ErrorResult.AccountsNotFound)

        // save activity
        val activity = Activity(account = account, amount = activity.amount, type = activity.type)
        activityAdapter.save(activity)
    }.fold(
        onSuccess = { ActionResult.Success(it) },
        onFailure = { ActionResult.Error(ErrorResult.InternalServerError) }
    ).also { println("Saved activity: $it") }


    override fun update(
        id: UUID,
        activity: ActivityUpdateDomain
    ): ActionResult<Activity> = runCatching {
        // get activity
        val activityToUpdate = activityAdapter.findById(id).orElse(null)
            ?: return ActionResult.Error(ErrorResult.ActivityNotFound)

        // create new activity
        val newActivity = activityToUpdate.copy(
            amount = activity.amount ?: activityToUpdate.amount,
            type = activity.type ?: activityToUpdate.type,
            paid = activity.paid ?: activityToUpdate.paid
        )

        // save activity
        activityAdapter.save(newActivity)
    }.fold(
        onSuccess = { ActionResult.Success(it) },
        onFailure = { ActionResult.Error(ErrorResult.InternalServerError) }
    ).also {
        println("Updated activity: $it")
    }

    override fun delete(id: UUID): ActionResult<Unit> = runCatching {
        if (!activityAdapter.existsById(id)) {
            return ActionResult.Error(ErrorResult.ActivityNotFound)
        }
        activityAdapter.deleteById(id)
    }.fold(
        onSuccess = { ActionResult.Success(Unit) },
        onFailure = { ActionResult.Error(ErrorResult.InternalServerError) }
    ).also {
        println("Deleted activity: $id")
    }

}
package gabi.cast.finance_api.domain.`in`.usecase.activity

import gabi.cast.finance_api.domain.`in`.ActivityService
import gabi.cast.finance_api.domain.`in`.entity.activity.Activity
import gabi.cast.finance_api.domain.shared.ActionResult
import gabi.cast.finance_api.domain.shared.ErrorResult
import gabi.cast.finance_api.infra.out.repository.ActivityAdapter
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class ActivityServiceImpl(
    private val activityAdapter: ActivityAdapter
) : ActivityService {

    override fun findByAccountMemberId(id: UUID): ActionResult {
        val results = activityAdapter.findByAccountMemberId(id)

        return when {
            results.isEmpty() -> ActionResult.Error(ErrorResult.ActivitiesForMemberNotFound)
            else -> ActionResult.Success(results)
        }
    }

    override fun findByAccountId(id: UUID): ActionResult {
        val results = activityAdapter.findByAccountId(id)

        return when {
            results.isEmpty() -> ActionResult.Error(ErrorResult.ActivitiesForMemberNotFound)
            else -> ActionResult.Success(results)
        }
    }


}
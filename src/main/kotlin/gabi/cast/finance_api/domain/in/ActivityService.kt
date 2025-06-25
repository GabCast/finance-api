package gabi.cast.finance_api.domain.`in`

import gabi.cast.finance_api.domain.`in`.entity.activity.Activity
import gabi.cast.finance_api.domain.shared.ActionResult
import gabi.cast.finance_api.infra.`in`.rest.model.ActivityDomain
import gabi.cast.finance_api.infra.`in`.rest.model.ActivityUpdateDomain
import java.util.UUID

interface ActivityService {
    fun findByAccountMemberId(id: UUID): ActionResult<List<Activity?>>
    fun findByAccountId(id: UUID) : ActionResult<List<Activity?>>
    fun save(activity: ActivityDomain) : ActionResult<Activity>
    fun update(id: UUID, activity: ActivityUpdateDomain) : ActionResult<Activity>
    fun delete(id: UUID) : ActionResult<Unit>
}
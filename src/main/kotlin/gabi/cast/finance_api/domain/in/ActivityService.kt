package gabi.cast.finance_api.domain.`in`

import gabi.cast.finance_api.domain.`in`.entity.activity.Activity
import gabi.cast.finance_api.domain.shared.ActionResult
import java.util.UUID

interface ActivityService {
    fun findByAccountMemberId(id: UUID): ActionResult<List<Activity?>>
    fun findByAccountId(id: UUID) : ActionResult<List<Activity?>>
}
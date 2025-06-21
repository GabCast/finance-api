package gabi.cast.finance_api.domain.`in`

import gabi.cast.finance_api.domain.shared.ActionResult
import java.util.UUID

interface ActivityService {
    fun findByAccountMemberId(id: UUID): ActionResult
    fun findByAccountId(id: UUID) : ActionResult
}
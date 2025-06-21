package gabi.cast.finance_api.domain.`in`

import gabi.cast.finance_api.domain.shared.ActionResult
import gabi.cast.finance_api.infra.`in`.rest.model.AccountDomain
import java.util.UUID

interface AccountService {

    fun findByMemberId(id: UUID) : ActionResult
    fun save(account: AccountDomain) : ActionResult
}
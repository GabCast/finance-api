package gabi.cast.finance_api.domain.`in`

import gabi.cast.finance_api.domain.`in`.entity.account.Account
import gabi.cast.finance_api.domain.shared.ActionResult
import gabi.cast.finance_api.infra.`in`.rest.model.AccountDomain

interface AccountService {

    fun findAll() : List<Account?>
    fun save(account: AccountDomain) : ActionResult
}
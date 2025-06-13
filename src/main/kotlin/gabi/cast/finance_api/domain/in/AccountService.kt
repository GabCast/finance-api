package gabi.cast.finance_api.domain.`in`

import gabi.cast.finance_api.domain.`in`.entity.account.Account

interface AccountService {

    fun findAll() : List<Account?>
    fun save(account: Account) : Account?
}
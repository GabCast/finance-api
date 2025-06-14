package gabi.cast.finance_api.domain.`in`.usecase.account

import gabi.cast.finance_api.domain.`in`.AccountService
import gabi.cast.finance_api.domain.`in`.entity.account.Account
import gabi.cast.finance_api.infra.out.repository.AccountAdapter
import org.springframework.stereotype.Service

@Service
class AccountServiceImpl(
    private val accountAdapter: AccountAdapter
) : AccountService {

    override fun findAll(): List<Account?> {
        return accountAdapter.findAll()
    }

    override fun save(account: Account): Account? {
        return accountAdapter.save(account)
    }

}

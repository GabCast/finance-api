package gabi.cast.finance_api.domain.`in`.usecase.account

import gabi.cast.finance_api.domain.`in`.AccountService
import gabi.cast.finance_api.domain.`in`.entity.account.Account
import gabi.cast.finance_api.infra.out.repository.AccountRepositoryImpl
import org.springframework.stereotype.Service

@Service
class AccountServiceImpl(
    private val accountRepositoryImpl: AccountRepositoryImpl
) : AccountService {

    override fun findAll(): List<Account?> {
        return accountRepositoryImpl.findAll()
    }

    override fun save(account: Account): Account? {
        return accountRepositoryImpl.save(account)
    }

}

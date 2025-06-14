package gabi.cast.finance_api.infra.out.repository

import gabi.cast.finance_api.domain.`in`.entity.account.Account
import gabi.cast.finance_api.domain.out.repository.AccountRepository
import org.springframework.stereotype.Repository

@Repository
interface AccountAdapter : AccountRepository {
    fun findByName(name: String): Account?
}
package gabi.cast.finance_api.infra.out.repository

import gabi.cast.finance_api.domain.`in`.entity.account.Account
import gabi.cast.finance_api.domain.out.repository.AccountRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface AccountAdapter : AccountRepository {
    fun findByName(name: String): Account?
    fun findByMemberId(memberId: UUID): List<Account?>
}
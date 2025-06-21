package gabi.cast.finance_api.domain.`in`.usecase.account

import gabi.cast.finance_api.domain.`in`.AccountService
import gabi.cast.finance_api.domain.`in`.entity.account.Account
import gabi.cast.finance_api.domain.shared.ActionResult
import gabi.cast.finance_api.domain.shared.ErrorResult
import gabi.cast.finance_api.infra.`in`.rest.model.AccountDomain
import gabi.cast.finance_api.infra.out.repository.AccountAdapter
import gabi.cast.finance_api.infra.out.repository.MemberAdapter
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class AccountServiceImpl(
    private val accountAdapter: AccountAdapter,
    private val memberAdapter: MemberAdapter
) : AccountService {

    override fun findByMemberId(id: UUID): ActionResult {
        val accounts = accountAdapter.findByMemberId(id)
        return when {
            accounts.isEmpty() -> ActionResult.Error(ErrorResult.AccountsNotFound)
            else -> ActionResult.Success(accounts)
        }
    }

    override fun save(account: AccountDomain): ActionResult {

        // retrieve the member or throw error
        val member = memberAdapter.findById(account.memberId).orElse(null) ?: return ActionResult.Error(ErrorResult.MemberNotFound)

        // save account
        val account = Account(member = member, name = account.name, currency = account.currency)

        return ActionResult.Success(accountAdapter.save(account))
    }

}

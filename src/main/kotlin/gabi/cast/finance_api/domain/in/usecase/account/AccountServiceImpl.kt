package gabi.cast.finance_api.domain.`in`.usecase.account

import gabi.cast.finance_api.domain.`in`.AccountService
import gabi.cast.finance_api.domain.`in`.entity.account.Account
import gabi.cast.finance_api.domain.shared.ActionResult
import gabi.cast.finance_api.domain.shared.ErrorResult
import gabi.cast.finance_api.domain.shared.toActionResult
import gabi.cast.finance_api.domain.shared.toActionResultIfNotEmpty
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

    override fun findById(id: UUID): ActionResult<Account> = runCatching {
        accountAdapter.findById(id)
    }.fold(
        onSuccess = { optional ->
            optional.toActionResult(ErrorResult.AccountNotFound)
        },
        onFailure = { ActionResult.Error(ErrorResult.InternalServerError) }
    ).also {
        println("Found account for id: $id")
        println("Result: $it")
    }

    override fun save(account: AccountDomain): ActionResult<Account> = runCatching {
        // retrieve the member or throw error
        val member = memberAdapter.findById(account.memberId).orElse(null)
            ?: return ActionResult.Error(ErrorResult.MemberNotFound)

        // save account
        val account = Account(member = member, name = account.name, currency = account.currency)
        accountAdapter.save(account)
    }.fold(
        onSuccess = { ActionResult.Success(it) },
        onFailure = { ActionResult.Error(ErrorResult.InternalServerError) }
    ).also {
        println("Saved account: $it")
    }

    override fun findByMemberId(id: UUID): ActionResult<List<Account?>> = runCatching {
        accountAdapter.findByMemberId(id)
    }.fold(
        onSuccess = { it.toActionResultIfNotEmpty(ErrorResult.AccountsNotFound) },
        onFailure = { ActionResult.Error(ErrorResult.InternalServerError) }
    ).also {
        println("Saved account: $it")
        println("Result: $it")
    }


}

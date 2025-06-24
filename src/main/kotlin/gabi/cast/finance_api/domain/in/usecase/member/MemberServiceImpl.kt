package gabi.cast.finance_api.domain.`in`.usecase.member

import gabi.cast.finance_api.domain.`in`.MemberService
import gabi.cast.finance_api.domain.`in`.entity.Member
import gabi.cast.finance_api.domain.shared.ActionResult
import gabi.cast.finance_api.domain.shared.ErrorResult
import gabi.cast.finance_api.domain.shared.toActionResultIf
import gabi.cast.finance_api.infra.`in`.rest.model.MemberDomain
import gabi.cast.finance_api.infra.out.repository.MemberAdapter
import org.springframework.stereotype.Service

@Service
class MemberServiceImpl(
    private val memberAdapter: MemberAdapter
) : MemberService {

    override fun findByReference(reference: String): ActionResult<Member?> = runCatching {
        memberAdapter.findByReference(reference)
    }.fold(
        onSuccess = { result ->
            result.toActionResultIf({ it?.id != null }, ErrorResult.AccountsNotFound)
        },
        onFailure = { ActionResult.Error(ErrorResult.InternalServerError) }
    ).also {
        println("Found member for reference: $reference")
        println("Result: $it")
    }

    override fun save(member: MemberDomain): ActionResult<Member> = runCatching {
        // create member
        val member = Member(reference = member.reference)
        // save member
        memberAdapter.save(member)
    }.fold(
        onSuccess = { ActionResult.Success(it) },
        onFailure = { ActionResult.Error(ErrorResult.InternalServerError) }
    ).also {
        println("Saved account: $it")
    }
}
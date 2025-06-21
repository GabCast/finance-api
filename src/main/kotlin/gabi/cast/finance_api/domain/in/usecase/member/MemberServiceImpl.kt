package gabi.cast.finance_api.domain.`in`.usecase.member

import gabi.cast.finance_api.domain.`in`.MemberService
import gabi.cast.finance_api.domain.`in`.entity.Member
import gabi.cast.finance_api.domain.shared.ActionResult
import gabi.cast.finance_api.domain.shared.ErrorResult
import gabi.cast.finance_api.infra.`in`.rest.model.MemberDomain
import gabi.cast.finance_api.infra.out.repository.MemberAdapter
import org.springframework.stereotype.Service

@Service
class MemberServiceImpl(
    private val memberAdapter: MemberAdapter
) : MemberService {

    override fun findByReference(reference: String): ActionResult {
        val member = memberAdapter.findByReference(reference)
        return when {
            member == null -> ActionResult.Error(ErrorResult.MemberNotFound)
            else -> ActionResult.Success(member)
        }
    }

    override fun save(member: MemberDomain): ActionResult {
        // save member
        val member = Member(reference = member.reference)

        return ActionResult.Success(memberAdapter.save(member))
    }

}

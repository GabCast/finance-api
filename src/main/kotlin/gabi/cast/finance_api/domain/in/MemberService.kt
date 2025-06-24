package gabi.cast.finance_api.domain.`in`

import gabi.cast.finance_api.domain.`in`.entity.Member
import gabi.cast.finance_api.domain.shared.ActionResult
import gabi.cast.finance_api.infra.`in`.rest.model.MemberDomain

interface MemberService {

    fun findByReference(reference: String) : ActionResult<Member?>
    fun save(member: MemberDomain) : ActionResult<Member>
}
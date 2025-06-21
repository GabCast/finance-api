package gabi.cast.finance_api.infra.out.rest

import gabi.cast.finance_api.domain.`in`.entity.Member
import java.util.UUID

data class MemberDTO(
    val id: UUID?,
    val reference: String,
)

fun Member.toDTO(): MemberDTO = MemberDTO(
    id = this.id,
    reference = this.reference,
)

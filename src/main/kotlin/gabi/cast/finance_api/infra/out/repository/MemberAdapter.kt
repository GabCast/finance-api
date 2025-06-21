package gabi.cast.finance_api.infra.out.repository

import gabi.cast.finance_api.domain.`in`.entity.Member
import gabi.cast.finance_api.domain.out.repository.MemberRepository
import org.springframework.stereotype.Repository

@Repository
interface MemberAdapter : MemberRepository {
    fun findByReference(reference: String): Member?
}
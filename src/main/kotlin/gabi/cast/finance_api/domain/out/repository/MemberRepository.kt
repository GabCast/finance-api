package gabi.cast.finance_api.domain.out.repository

import gabi.cast.finance_api.domain.`in`.entity.Member
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface MemberRepository : JpaRepository<Member, UUID> {
    fun findByReference(reference: String): Member?
}

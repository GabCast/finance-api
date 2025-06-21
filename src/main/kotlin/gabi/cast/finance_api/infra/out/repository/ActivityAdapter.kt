package gabi.cast.finance_api.infra.out.repository

import gabi.cast.finance_api.domain.`in`.entity.activity.Activity
import gabi.cast.finance_api.domain.out.repository.ActivityRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface ActivityAdapter : ActivityRepository {
    fun findByAccountMemberId(memberId: UUID): List<Activity?>
    fun findByAccountId(accountId: UUID): List<Activity?>
}
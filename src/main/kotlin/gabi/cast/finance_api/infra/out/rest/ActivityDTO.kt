package gabi.cast.finance_api.infra.out.rest

import gabi.cast.finance_api.domain.`in`.entity.Comment
import gabi.cast.finance_api.domain.`in`.entity.activity.Activity
import gabi.cast.finance_api.domain.shared.ActivityType
import java.util.UUID

data class ActivityDTO(
    val id: UUID?,
    val amount: Double,
    val accountId: UUID?,
    val memberReference: String,
    val comments: List<Comment>?,
    val type: ActivityType,
    val paid: Boolean = false,
)

fun Activity.toDTO(): ActivityDTO = ActivityDTO(
    id = this.id,
    amount = this.amount,
    accountId = this.account.id,
    memberReference = this.account.member.reference,
    type = this.type,
    paid = this.paid,
    comments = this.comments
)

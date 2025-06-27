package gabi.cast.finance_api.infra.out.rest

import gabi.cast.finance_api.domain.`in`.entity.account.Account
import gabi.cast.finance_api.domain.`in`.entity.activity.Activity
import gabi.cast.finance_api.domain.shared.ActivityType
import gabi.cast.finance_api.domain.shared.Currency
import java.util.UUID

data class AccountDTO(
    val id: UUID?,
    val name: String,
    val memberReference: String,
    val currency: Currency,
    val balance: Double,
    val createdAt: String,
    val updatedAt: String? = null,
)

fun Account.toDTO(): AccountDTO = AccountDTO(
    id = this.id,
    name = this.name,
    currency = this.currency,
    balance = this.activities.toBalance(),
    memberReference = this.member.reference,
    createdAt = this.createdAt.toString(),
    updatedAt = this.updatedAt?.toString()
)

fun List<Activity?>.toBalance(): Double = this
    .filter { it?.paid == true }
    .sumOf {
        when (it?.type) {
            ActivityType.CREDIT -> it.amount
            ActivityType.DEBIT -> -it.amount
            else -> 0.0
        }
    }

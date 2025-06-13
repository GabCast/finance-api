package gabi.cast.finance_api.infra.out.rest

import gabi.cast.finance_api.domain.`in`.entity.account.Account
import gabi.cast.finance_api.domain.shared.Currency
import java.util.UUID

data class AccountDTO(
    val id: UUID?,
    val name: String,
    val memberReference: String,
    val currency: Currency,
    val createdAt: String,
    val updatedAt: String? = null,
)

fun Account.toDTO(): AccountDTO = AccountDTO(
    id = this.id,
    name = this.name,
    currency = this.currency,
    memberReference = this.member.reference,
    createdAt = this.createdAt.toString(),
    updatedAt = this.updatedAt?.toString()
)

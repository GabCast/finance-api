package gabi.cast.finance_api.infra.`in`.rest.model

import gabi.cast.finance_api.domain.shared.ActivityType
import java.util.UUID

data class ActivityDomain (
    val accountId: UUID,
    val amount: Double,
    val type: ActivityType,
    val paid: Boolean
)
package gabi.cast.finance_api.infra.`in`.rest.model

import gabi.cast.finance_api.domain.shared.ActivityType

data class ActivityUpdateDomain (
    val amount: Double? = null,
    val type: ActivityType? = null,
    val paid: Boolean? = null
)
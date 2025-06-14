package gabi.cast.finance_api.domain.out.repository

import gabi.cast.finance_api.domain.`in`.entity.activity.Activity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ActivityRepository : JpaRepository<Activity, UUID>

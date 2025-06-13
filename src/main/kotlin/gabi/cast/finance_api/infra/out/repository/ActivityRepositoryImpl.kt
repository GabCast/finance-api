package gabi.cast.finance_api.infra.out.repository

import gabi.cast.finance_api.domain.`in`.entity.activity.Activity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface ActivityRepositoryImpl : JpaRepository<Activity, UUID>

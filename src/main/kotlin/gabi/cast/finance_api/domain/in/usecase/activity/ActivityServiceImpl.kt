package gabi.cast.finance_api.domain.`in`.usecase.activity

import gabi.cast.finance_api.domain.`in`.ActivityService
import gabi.cast.finance_api.domain.`in`.entity.activity.Activity
import gabi.cast.finance_api.infra.out.repository.ActivityRepositoryImpl
import org.springframework.stereotype.Service

@Service
class ActivityServiceImpl(
    private val activityRepositoryImpl: ActivityRepositoryImpl
) : ActivityService {

    override fun findAll(): List<Activity?> {
        return activityRepositoryImpl.findAll()
    }

}
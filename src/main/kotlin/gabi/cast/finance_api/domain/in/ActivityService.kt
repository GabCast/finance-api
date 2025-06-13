package gabi.cast.finance_api.domain.`in`

import gabi.cast.finance_api.domain.`in`.entity.activity.Activity

interface ActivityService {

    fun findAll() : List<Activity?>
}
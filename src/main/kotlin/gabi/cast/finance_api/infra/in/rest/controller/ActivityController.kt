package gabi.cast.finance_api.infra.`in`.rest.controller

import gabi.cast.finance_api.infra.out.rest.ActivityDTO
import gabi.cast.finance_api.infra.out.rest.toDTO
import gabi.cast.finance_api.domain.`in`.ActivityService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/activity")
class ActivityController (private val activityService: ActivityService) {

    @Operation(summary = "accounts section")
    @GetMapping()
    fun account() : List<ActivityDTO?> {
        return activityService.findAll().map { activities ->
            activities?.toDTO()
        }
    }
}

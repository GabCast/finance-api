package gabi.cast.finance_api.infra.`in`.rest.controller

import gabi.cast.finance_api.infra.out.rest.toDTO
import gabi.cast.finance_api.domain.`in`.ActivityService
import gabi.cast.finance_api.domain.shared.mapList
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/activity")
class ActivityController(private val activityService: ActivityService) {

    @Operation(summary = "activity by member", description = "Returns all activities for the specified member")
    @GetMapping("/member/{memberId}")
    fun activitiesByMember(@PathVariable memberId: UUID): ResponseEntity<*> =
        activityService.findByAccountMemberId(memberId).mapList({ it?.toDTO() }, successStatus = HttpStatus.OK)

    @Operation(summary = "activity by account", description = "Returns all activities for the specified account")
    @GetMapping("/account/{accountId}")
    fun activitiesByAccount(@PathVariable accountId: UUID): ResponseEntity<*> =
        activityService.findByAccountId(accountId).mapList({ it?.toDTO() }, successStatus = HttpStatus.OK)
}

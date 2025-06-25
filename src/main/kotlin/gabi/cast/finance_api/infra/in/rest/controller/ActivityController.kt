package gabi.cast.finance_api.infra.`in`.rest.controller

import gabi.cast.finance_api.infra.out.rest.toDTO
import gabi.cast.finance_api.domain.`in`.ActivityService
import gabi.cast.finance_api.domain.shared.map
import gabi.cast.finance_api.domain.shared.mapList
import gabi.cast.finance_api.domain.shared.mapUnit
import gabi.cast.finance_api.infra.`in`.rest.model.ActivityDomain
import gabi.cast.finance_api.infra.`in`.rest.model.ActivityUpdateDomain
import gabi.cast.finance_api.infra.`in`.rest.model.CommentDomain
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.parameters.RequestBody as SwaggerRequestBody
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
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

    @Operation(
        summary = "Create a new activity",
        description = "Creates a new activity with the specified data.",
        requestBody = SwaggerRequestBody(
            description = "Comment to be created",
            required = true,
            content = [Content(schema = Schema(implementation = CommentDomain::class))]
        )
    )
    @PostMapping()
    fun create(@RequestBody activity: ActivityDomain): ResponseEntity<*> =
        activityService.save(activity).map({ it.toDTO() }, successStatus = HttpStatus.CREATED)

    @Operation(
        summary = "Update an activity",
        description = "Update an activity with the specified data.",
        requestBody = SwaggerRequestBody(
            description = "Activity to be updated",
            required = true,
            content = [Content(schema = Schema(implementation = ActivityUpdateDomain::class))]
        )
    )
    @PutMapping("/{activityId}")
    fun update(
        @PathVariable activityId: UUID,
        @RequestBody activityUpdateDomain: ActivityUpdateDomain
    ): ResponseEntity<*> =
        activityService.update(activityId, activityUpdateDomain).map({ it.toDTO() }, successStatus = HttpStatus.OK)

    @Operation(
        summary = "Delete an activity",
        description = "Delete an activity with the specified data.",
    )
    @PutMapping("/{activityId}")
    fun delete(
        @PathVariable activityId: UUID
    ): ResponseEntity<*> =
        activityService.delete(activityId).mapUnit(successStatus = HttpStatus.OK)
}

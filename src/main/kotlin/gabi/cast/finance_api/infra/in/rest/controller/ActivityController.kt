package gabi.cast.finance_api.infra.`in`.rest.controller

import gabi.cast.finance_api.infra.out.rest.toDTO
import gabi.cast.finance_api.domain.`in`.ActivityService
import gabi.cast.finance_api.domain.shared.ErrorResult
import gabi.cast.finance_api.domain.shared.map
import gabi.cast.finance_api.domain.shared.mapList
import gabi.cast.finance_api.domain.shared.mapUnit
import gabi.cast.finance_api.infra.`in`.rest.model.ActivityDomain
import gabi.cast.finance_api.infra.`in`.rest.model.ActivityUpdateDomain
import gabi.cast.finance_api.infra.`in`.rest.model.CommentDomain
import gabi.cast.finance_api.infra.out.rest.ActivityDTO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import jakarta.validation.Valid
import io.swagger.v3.oas.annotations.parameters.RequestBody as SwaggerRequestBody
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
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

    @Operation(
        summary = "activity by member", description = "Returns all activities for the specified member",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Activities by member id returned successfully.",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ActivityDTO::class)
                )]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Invalid member id provided. Must be a UUID with hyphens, without dashes or spaces."
            ),
            ApiResponse(
                responseCode = "404", description = "MEMBER NOT FOUND or ACTIVITIES NOT FOUND", content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ErrorResult.MemberNotFound::class)
                )]
            ),
            ApiResponse(
                responseCode = "500", description = "Internal Server Error", content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ErrorResult.InternalServerError::class)
                )]
            )
        ]
    )
    @GetMapping("/member/{memberId}")
    fun activitiesByMember(@PathVariable memberId: UUID): ResponseEntity<*> =
        activityService.findByAccountMemberId(memberId).mapList({ it?.toDTO() }, successStatus = HttpStatus.OK)

    @Operation(
        summary = "activity by account", description = "Returns all activities for the specified account",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Activities by account id returned successfully.",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ActivityDTO::class)
                )]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Invalid account id provided. Must be a UUID with hyphens, without dashes or spaces."
            ),
            ApiResponse(
                responseCode = "404", description = "ACTIVITY NOT FOUND", content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ErrorResult.ActivityNotFound::class)
                )]
            ),
            ApiResponse(
                responseCode = "500", description = "Internal Server Error", content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ErrorResult.InternalServerError::class)
                )]
            )
        ]
    )
    @GetMapping("/account/{accountId}")
    fun activitiesByAccount(@PathVariable accountId: UUID): ResponseEntity<*> =
        activityService.findByAccountId(accountId).mapList({ it?.toDTO() }, successStatus = HttpStatus.OK)

    @Operation(
        summary = "Create a new activity",
        description = "Creates a new activity with the specified data.",
        requestBody = SwaggerRequestBody(
            description = "Activity to be created",
            required = true,
            content = [Content(schema = Schema(implementation = CommentDomain::class))]
        ),
        responses = [
            ApiResponse(
                responseCode = "201",
                description = "Activity created successfully. Returned activity data",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ActivityDTO::class)
                )]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Invalid activity data provided. Must be a valid UUID with hyphens, without dashes or spaces."
            ),
            ApiResponse(
                responseCode = "404", description = "ACCOUNT NOT FOUND", content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ErrorResult.AccountsNotFound::class)
                )]
            ),
            ApiResponse(
                responseCode = "500", description = "Internal Server Error", content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ErrorResult.InternalServerError::class)
                )]
            )
        ]
    )
    @PostMapping()
    fun create(@Valid @RequestBody activity: ActivityDomain): ResponseEntity<*> =
        activityService.save(activity).map({ it.toDTO() }, successStatus = HttpStatus.CREATED)

    @Operation(
        summary = "Update an activity",
        description = "Update an activity with the specified data.",
        requestBody = SwaggerRequestBody(
            description = "Activity to be updated",
            required = true,
            content = [Content(schema = Schema(implementation = ActivityUpdateDomain::class))]
        ),
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Activity updated successfully. Returned activity data",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ActivityDTO::class)
                )]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Invalid activity data provided. Must be a valid UUID with hyphens, without dashes or spaces."
            ),
            ApiResponse(
                responseCode = "404", description = "ACCOUNT NOT FOUND", content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ErrorResult.AccountsNotFound::class)
                )]
            ),
            ApiResponse(
                responseCode = "500", description = "Internal Server Error", content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ErrorResult.InternalServerError::class)
                )]
            )
        ]
    )
    @PutMapping("/{activityId}")
    fun update(
        @PathVariable activityId: UUID,
        @Valid @RequestBody activityUpdateDomain: ActivityUpdateDomain
    ): ResponseEntity<*> =
        activityService.update(activityId, activityUpdateDomain).map({ it.toDTO() }, successStatus = HttpStatus.OK)

    @Operation(
        summary = "Delete an activity",
        description = "Delete an activity with the specified data.",
        responses = [
            ApiResponse(responseCode = "200", description = "Activity deleted successfully."),
            ApiResponse(
                responseCode = "400",
                description = "Invalid activity data provided. Must be a valid UUID with hyphens, without dashes or spaces."
            ),
            ApiResponse(
                responseCode = "404", description = "ACTIVITY NOT FOUND", content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ErrorResult.ActivityNotFound::class)
                )]
            ),
            ApiResponse(
                responseCode = "500", description = "Internal Server Error", content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ErrorResult.InternalServerError::class)
                )]
            )
        ]
    )
    @DeleteMapping("/{activityId}")
    fun delete(
        @PathVariable activityId: UUID
    ): ResponseEntity<*> =
        activityService.delete(activityId).mapUnit(successStatus = HttpStatus.OK)
}

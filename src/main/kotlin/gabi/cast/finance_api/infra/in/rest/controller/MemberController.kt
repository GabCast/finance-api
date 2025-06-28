package gabi.cast.finance_api.infra.`in`.rest.controller

import gabi.cast.finance_api.domain.`in`.MemberService
import gabi.cast.finance_api.domain.shared.ErrorResult
import gabi.cast.finance_api.domain.shared.map
import gabi.cast.finance_api.infra.`in`.rest.model.MemberDomain
import gabi.cast.finance_api.infra.out.rest.MemberDTO
import gabi.cast.finance_api.infra.out.rest.toDTO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import io.swagger.v3.oas.annotations.parameters.RequestBody as SwaggerRequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/member")
class MemberController(private val memberService: MemberService) {

    @Operation(
        summary = "Get member by reference",
        responses = [
            ApiResponse(
                responseCode = "200", description = "Returned member data", content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = MemberDTO::class)
                )]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Invalid reference provided. Must be a UUID with hyphens, without dashes or spaces.",
            ),
            ApiResponse(
                responseCode = "404", description = "MEMBER NOT FOUND", content = [Content(
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
    @GetMapping("/reference/{reference}")
    fun member(@PathVariable(required = true) reference: String): ResponseEntity<*> =
        memberService.findByReference(reference).map(transform = { it?.toDTO() }, successStatus = HttpStatus.OK)

    @Operation(
        summary = "Create a new member",
        description = "Creates a new member with the specified data.",
        requestBody = SwaggerRequestBody(
            description = "Member to be created",
            required = true,
            content = [Content(schema = Schema(implementation = MemberDomain::class))]
        ),
        responses = [
            ApiResponse(
                responseCode = "201", description = "Member created successfully. Returned member data",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = MemberDTO::class)
                )]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Invalid member data provided. Must be a valid UUID with hyphens, without dashes or spaces."
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
    fun createMember(@Valid @RequestBody(required = true) member: MemberDomain): ResponseEntity<*> =
        memberService.save(member).map(transform = { it.toDTO() }, successStatus = HttpStatus.CREATED)
}
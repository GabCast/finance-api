package gabi.cast.finance_api.infra.`in`.rest.controller

import gabi.cast.finance_api.domain.`in`.MemberService
import gabi.cast.finance_api.domain.`in`.entity.Member
import gabi.cast.finance_api.domain.shared.ActionResult
import gabi.cast.finance_api.infra.`in`.rest.model.MemberDomain
import gabi.cast.finance_api.infra.`in`.rest.resource.BaseResponseBody
import gabi.cast.finance_api.infra.`in`.rest.resource.ErrorResponse
import gabi.cast.finance_api.infra.out.rest.MemberDTO
import gabi.cast.finance_api.infra.out.rest.toDTO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
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

    @Operation(summary = "member section")
    @GetMapping("/reference/{reference}")
    fun member(@PathVariable(required = true) reference: String) : ResponseEntity<*> {
        return when (val actionResult =
            memberService.findByReference(reference)) {
            is ActionResult.Error -> {
                val errorResponse =
                    ErrorResponse.from(
                        actionResult.error
                    )
                ResponseEntity.status(errorResponse.httpStatusCode).body(errorResponse)
            }

            is ActionResult.Success -> BaseResponseBody.from<Member, MemberDTO>(actionResult)
            { it?.toDTO() }.asEntity(successHttpStatus = HttpStatus.OK)
        }
    }

    @Operation(
        summary = "Create a new member",
        description = "Creates a new member with the specified data.",
        requestBody = SwaggerRequestBody(
            description = "Member to be created",
            required = true,
            content = [Content(schema = Schema(implementation = MemberDomain::class))]
        )
    )
    @PostMapping()
    fun createMember(@RequestBody(required = true) member: MemberDomain) : ResponseEntity<*> {

        return when (val actionResult =
            memberService.save(member)) {
            is ActionResult.Error -> {
                val errorResponse =
                    ErrorResponse.from(
                        actionResult.error
                    )
                ResponseEntity.status(errorResponse.httpStatusCode).body(errorResponse)
            }

            is ActionResult.Success -> BaseResponseBody.from<Member, MemberDTO>(actionResult)
            { it?.toDTO() }.asEntity(successHttpStatus = HttpStatus.CREATED)
        }
    }
}
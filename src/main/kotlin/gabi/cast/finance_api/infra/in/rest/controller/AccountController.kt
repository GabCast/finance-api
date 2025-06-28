package gabi.cast.finance_api.infra.`in`.rest.controller

import gabi.cast.finance_api.infra.out.rest.toDTO
import gabi.cast.finance_api.domain.`in`.AccountService
import gabi.cast.finance_api.domain.shared.ErrorResult
import gabi.cast.finance_api.domain.shared.map
import gabi.cast.finance_api.domain.shared.mapList
import gabi.cast.finance_api.infra.`in`.rest.model.AccountDomain
import gabi.cast.finance_api.infra.out.rest.AccountDTO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestBody
import io.swagger.v3.oas.annotations.parameters.RequestBody as SwaggerRequestBody
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import java.util.UUID

@RestController
@RequestMapping("/account")
class AccountController(private val accountsService: AccountService) {

    @Operation(
        summary = "account by id",
        responses = [
            ApiResponse(
                responseCode = "200", description = "Returned member data", content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = AccountDTO::class)
                )]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Invalid account id provided. Must be a UUID with hyphens, without dashes or spaces."
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
    @GetMapping("/{accountId}")
    fun account(@PathVariable(required = true) accountId: UUID): ResponseEntity<*> =
        accountsService.findById(accountId).map(transform = { it.toDTO() }, successStatus = HttpStatus.OK)

    @Operation(
        summary = "accounts by memberId", description = "Returns all accounts for the specified memberId",
        responses = [
            ApiResponse(
                responseCode = "200", description = "Returned member data", content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = AccountDTO::class)
                )]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Invalid member id provided. Must be a UUID with hyphens, without dashes or spaces."
            ),
            ApiResponse(
                responseCode = "404", description = "MEMBER NOT FOUND or ACCOUNTS NOT FOUND.", content = [Content(
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
    fun accounts(@PathVariable(required = true) memberId: UUID): ResponseEntity<*> =
        accountsService.findByMemberId(memberId)
            .mapList(transform = { it?.toDTO() }, successStatus = HttpStatus.OK)

    @Operation(
        summary = "Create a new account",
        description = "Creates a new account with the specified data.",
        requestBody = SwaggerRequestBody(
            description = "Account to be created",
            required = true,
            content = [Content(schema = Schema(implementation = AccountDomain::class))]
        ),
        responses = [
            ApiResponse(
                responseCode = "201",
                description = "Account created successfully. Returned account data",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = AccountDTO::class)
                )]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Invalid account data provided. Must be a valid UUID with hyphens, without dashes or spaces."
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
    @PostMapping()
    fun createAccount(@Valid @RequestBody(required = true) account: AccountDomain): ResponseEntity<*> =
        accountsService.save(account).map(transform = { it.toDTO() }, successStatus = HttpStatus.CREATED)
}

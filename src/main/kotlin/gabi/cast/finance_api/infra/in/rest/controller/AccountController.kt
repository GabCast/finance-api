package gabi.cast.finance_api.infra.`in`.rest.controller

import gabi.cast.finance_api.infra.out.rest.AccountDTO
import gabi.cast.finance_api.infra.out.rest.toDTO
import gabi.cast.finance_api.domain.`in`.AccountService
import gabi.cast.finance_api.domain.`in`.entity.account.Account
import gabi.cast.finance_api.domain.shared.ActionResult
import gabi.cast.finance_api.infra.`in`.rest.model.AccountDomain
import gabi.cast.finance_api.infra.`in`.rest.resource.BaseResponseBody
import gabi.cast.finance_api.infra.`in`.rest.resource.ErrorResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
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
class AccountController (private val accountsService: AccountService) {

    @Operation(summary = "accounts section")
    @GetMapping("/member/{memberId}")
    fun accounts(@PathVariable(required = true) memberId: UUID) : ResponseEntity<*> {
        return when (val actionResult =
            accountsService.findByMemberId(memberId)) {
            is ActionResult.Error -> {
                val errorResponse =
                    ErrorResponse.from(
                        actionResult.error
                    )
                ResponseEntity.status(errorResponse.httpStatusCode).body(errorResponse)
            }

            is ActionResult.Success -> BaseResponseBody.from<List<Account>, List<AccountDTO>>(actionResult)
            { accounts -> accounts?.map { it.toDTO() } }.asEntity(successHttpStatus = HttpStatus.OK)
        }
    }

    @Operation(
        summary = "Create a new account",
        description = "Creates a new account with the specified data.",
        requestBody = SwaggerRequestBody(
            description = "Account to be created",
            required = true,
            content = [Content(schema = Schema(implementation = AccountDomain::class))]
        )
    )
    @PostMapping()
    fun createAccount(@RequestBody(required = true) account: AccountDomain) : ResponseEntity<*> {

        return when (val actionResult =
            accountsService.save(account)) {
            is ActionResult.Error -> {
                val errorResponse =
                    ErrorResponse.from(
                        actionResult.error
                    )
                ResponseEntity.status(errorResponse.httpStatusCode).body(errorResponse)
            }

            is ActionResult.Success -> BaseResponseBody.from<Account, AccountDTO>(actionResult)
            { it?.toDTO() }.asEntity(successHttpStatus = HttpStatus.CREATED)
        }
    }
}

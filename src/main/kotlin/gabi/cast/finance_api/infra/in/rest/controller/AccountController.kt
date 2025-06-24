package gabi.cast.finance_api.infra.`in`.rest.controller

import gabi.cast.finance_api.infra.out.rest.toDTO
import gabi.cast.finance_api.domain.`in`.AccountService
import gabi.cast.finance_api.domain.shared.map
import gabi.cast.finance_api.domain.shared.mapList
import gabi.cast.finance_api.infra.`in`.rest.model.AccountDomain
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
class AccountController(private val accountsService: AccountService) {

    @Operation(summary = "account by Id")
    @GetMapping("/{accountId}")
    fun account(@PathVariable(required = true) accountId: UUID): ResponseEntity<*> =
        accountsService.findById(accountId).map(transform = { it.toDTO() }, successStatus = HttpStatus.OK)

    @Operation(summary = "accounts section")
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
        )
    )
    @PostMapping()
    fun createAccount(@RequestBody(required = true) account: AccountDomain): ResponseEntity<*> =
        accountsService.save(account).map(transform = { it.toDTO() }, successStatus = HttpStatus.OK)
}

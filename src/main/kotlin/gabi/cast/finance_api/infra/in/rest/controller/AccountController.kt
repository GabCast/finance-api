package gabi.cast.finance_api.infra.`in`.rest.controller

import gabi.cast.finance_api.domain.`in`.entity.account.Account
import gabi.cast.finance_api.infra.out.rest.AccountDTO
import gabi.cast.finance_api.infra.out.rest.toDTO
import gabi.cast.finance_api.domain.`in`.AccountService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import io.swagger.v3.oas.annotations.parameters.RequestBody


@RestController
@RequestMapping("/account")
class AccountController (private val accountsService: AccountService) {

    @Operation(summary = "accounts section")
    @GetMapping()
    fun account() : List<AccountDTO?> {
        return accountsService.findAll().map { accounts ->
            accounts?.toDTO()
        }
    }

    @Operation(
        summary = "Create a new account",
        description = "Creates a new account with the specified data.",
        requestBody = RequestBody(
            description = "Account to be created",
            required = true,
            content = [Content(schema = Schema(implementation = Account::class))]
        )
    )
    @PostMapping()
    fun account(@RequestBody account: Account) : AccountDTO? {
        return accountsService.save(account)?.toDTO()
    }
}

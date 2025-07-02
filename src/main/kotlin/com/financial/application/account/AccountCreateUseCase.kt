package com.financial.application.account

import com.financial.application.UseCase
import com.financial.domain.account.Account
import com.financial.domain.account.AccountGateway

class AccountCreateUseCase(
    private val accountGateway: AccountGateway
) : UseCase<AccountCreateUseCase.Input, AccountCreateUseCase.Output> {

    override fun execute(input: Input): Output {
        val account = Account.create(input.name())

        val result = this.accountGateway.create(account)

        return StdOutput(result.id().value().toString())
    }

    interface Input {
        fun name(): String
    }

    interface Output {
        fun id(): String
    }

    data class StdOutput(val id: String) : Output {
        override fun id(): String = this.id
    }
}
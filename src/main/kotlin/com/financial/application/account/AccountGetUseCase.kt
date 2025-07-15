package com.financial.application.account

import com.financial.application.UseCase
import com.financial.domain.account.AccountGateway
import com.financial.domain.account.AccountID
import java.util.*

class AccountGetUseCase(
    private val accountGateway: AccountGateway
) : UseCase<String, Optional<AccountGetUseCase.Output>> {

    override fun execute(input: String): Optional<Output> {
        val accountId = AccountID.with(input)

        val result = this.accountGateway.getByIdAndDeletedAtIsNull(accountId)

        return result
            .map { it.id().value().toString() }
            .map(::StdOutput)
    }

    interface Output {
        fun id(): String
    }

    data class StdOutput(val id: String) : Output {
        override fun id(): String = this.id
    }
}
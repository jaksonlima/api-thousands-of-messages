package com.financial.application.account

import com.financial.application.UseCase
import com.financial.domain.account.Account
import com.financial.domain.account.AccountGateway
import com.financial.domain.account.AccountID
import java.time.Instant
import java.util.*

class AccountGetUseCase(
    private val accountGateway: AccountGateway
) : UseCase<String, Optional<AccountGetUseCase.Output>> {

    override fun execute(input: String): Optional<Output> {
        val accountId = AccountID.with(input)

        val result = this.accountGateway.getByIdAndDeletedAtIsNull(accountId)

        return result.map(StdOutput::from)
    }

    interface Output {
        fun id(): String
        fun name(): String
        fun createdAt(): Instant
        fun updatedAt(): Instant
    }

    data class StdOutput(
        val id: String,
        val name: String,
        val createdAt: Instant,
        val updatedAt: Instant
    ) : Output {
        companion object {
            fun from(account: Account): StdOutput {
                return StdOutput(
                    id = account.id().toString(),
                    name = account.name,
                    createdAt = account.createdAt,
                    updatedAt = account.updatedAt
                )
            }
        }

        override fun id(): String = this.id
        override fun name(): String = this.name
        override fun createdAt(): Instant = this.createdAt
        override fun updatedAt(): Instant = this.updatedAt
    }
}
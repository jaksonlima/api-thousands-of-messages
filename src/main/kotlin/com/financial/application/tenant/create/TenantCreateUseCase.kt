package com.financial.application.tenant.create

import com.financial.application.UseCase
import com.financial.domain.account.Account
import com.financial.domain.account.AccountGateway
import com.financial.domain.account.AccountID
import com.financial.domain.expections.NotFoundException
import com.financial.domain.tenant.Tenant
import com.financial.domain.tenant.TenantGateway

class TenantCreateUseCase(
    private val accountGateway: AccountGateway,
    private val tenantGateway: TenantGateway
) : UseCase<TenantCreateUseCase.Input, TenantCreateUseCase.Output> {

    override fun execute(input: Input): Output {
        val accountId = AccountID.with(input.accountId())

        val account = this.accountGateway.getByIdAndDeletedAtIsNull(accountId)

        if (account == null) {
            println()
        }

        val tenant = this.tenantGateway.create(Tenant.create(accountId))

        return StdOutput(tenant.id().value().toString())
    }

    interface Input {
        fun accountId(): String
    }

    interface Output {
        fun id(): String
    }

    data class StdOutput(val id: String) : Output {
        override fun id(): String = this.id;
    }
}
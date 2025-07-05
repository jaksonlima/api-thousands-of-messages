package com.financial.application.tenant

import com.financial.application.UseCase
import com.financial.domain.account.AccountGateway
import com.financial.domain.account.AccountID
import com.financial.domain.tenant.Tenant
import com.financial.domain.tenant.TenantGateway

class TenantCreateUseCase(
    private val accountGateway: AccountGateway,
    private val tenantGateway: TenantGateway
) : UseCase<TenantCreateUseCase.Input, TenantCreateUseCase.Output> {

    override fun execute(input: Input): Output {
        val accountId = AccountID.Companion.with(input.accountId())

        val account = this.accountGateway.getByIdAndDeletedAtIsNull(accountId)

        val tenant = Tenant.create(accountId)

        val event = if (account == null) {
            tenant.createTenantEventAccountNotFound()
        } else {
            tenant.createTenantEventProcessing()
        }

        this.tenantGateway.create(tenant)
        this.tenantGateway.create(event)

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
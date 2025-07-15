package com.financial.application.tenant

import com.financial.application.UseCase
import com.financial.domain.account.AccountID
import com.financial.domain.tenant.Tenant
import com.financial.domain.tenant.TenantGateway

class TenantCreateUseCase(
    private val tenantGateway: TenantGateway
) : UseCase<String, TenantCreateUseCase.Output> {

    override fun execute(input: String): Output {
        val accountId = AccountID.with(input)

        val created = this.tenantGateway.create(Tenant.create(accountId))

        return StdOutput(created.id().value().toString())
    }

    interface Output {
        fun id(): String
    }

    data class StdOutput(val id: String) : Output {
        override fun id(): String = this.id;
    }
}
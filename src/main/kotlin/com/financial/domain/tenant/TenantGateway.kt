package com.financial.domain.tenant

import com.financial.domain.account.AccountID
import java.util.Optional

interface TenantGateway {
    fun create(tenant: Tenant): Tenant
    fun findByAccountId(accountId: AccountID): Optional<Tenant>
    fun createSchema(tenant: Tenant): Boolean
}
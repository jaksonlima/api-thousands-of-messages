package com.financial.domain.tenant

interface TenantGateway {
    fun create(tenant: Tenant): Tenant
}
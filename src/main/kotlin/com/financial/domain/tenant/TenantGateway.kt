package com.financial.domain.tenant

interface TenantGateway {
    fun create(tenant: Tenant): Tenant
    fun create(tenantEvent: TenantEvent): TenantEvent
}
package com.financial.infrastructure.tenant

import com.financial.domain.tenant.Tenant
import com.financial.domain.tenant.TenantGateway
import com.financial.domain.tenant.TenantID
import java.util.concurrent.ConcurrentHashMap

class TenantInMemoryGateway() : TenantGateway {

    companion object {
        private val db: MutableMap<TenantID, Tenant> = ConcurrentHashMap()
    }

    override fun create(tenant: Tenant): Tenant {
        db.putIfAbsent(tenant.id(), tenant)
        return tenant
    }

}
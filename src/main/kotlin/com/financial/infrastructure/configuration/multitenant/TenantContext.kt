package com.financial.infrastructure.configuration.multitenant

object TenantContext {
    private val currentTenant: ThreadLocal<String> = ThreadLocal.withInitial { "public" }

    fun setCurrentTenant(tenant: String) {
        currentTenant.set(tenant)
    }

    fun getCurrentTenant(): String {
        return currentTenant.get()
    }

    fun clear() {
        currentTenant.remove()
    }
}
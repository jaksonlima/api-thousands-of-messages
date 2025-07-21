package com.financial.infrastructure.configuration.multitenant

import org.hibernate.context.spi.CurrentTenantIdentifierResolver
import org.springframework.stereotype.Component

@Component
class CurrentTenantIdentifierResolverImpl : CurrentTenantIdentifierResolver<String> {

    override fun resolveCurrentTenantIdentifier(): String {
        return TenantContext.getCurrentTenant()
    }

    override fun validateExistingCurrentSessions(): Boolean {
        return true
    }
}
package com.financial.infrastructure.configuration.usecases

import com.financial.application.tenant.TenantCreateUseCase
import com.financial.domain.tenant.TenantGateway
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration(proxyBeanMethods = false)
class TenantUseCaseConfig(
    val tenantGateway: TenantGateway
) {

    @Bean
    fun tenantCreateUseCase(): TenantCreateUseCase {
        return TenantCreateUseCase(tenantGateway)
    }

}
package com.financial.infrastructure.configuration.usecases

import com.financial.application.account.AccountCreateUseCase
import com.financial.domain.account.AccountGateway
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration(proxyBeanMethods = false)
class AccountUseCaseConfig(
    val accountGateway: AccountGateway
) {

    @Bean
    fun accountCreateUseCase(): AccountCreateUseCase {
        return AccountCreateUseCase(accountGateway)
    }

}
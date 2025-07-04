package com.financial.infrastructure.configuration.usecases

import com.financial.application.wallet.WalletCreateUseCase
import com.financial.application.wallet.WalletGetUseCase
import com.financial.domain.wallet.WalletGateway
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration(proxyBeanMethods = false)
class WallerUseCaseConfig(
    val walletGateway: WalletGateway
) {

    @Bean
    fun createWalletUseCase(): WalletCreateUseCase {
        return WalletCreateUseCase(walletGateway)
    }

    @Bean
    fun getWalletUseCase(): WalletGetUseCase {
        return WalletGetUseCase(walletGateway)
    }
}
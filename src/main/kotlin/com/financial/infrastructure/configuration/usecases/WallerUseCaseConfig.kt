package com.financial.infrastructure.configuration.usecases

import com.financial.application.wallet.create.CreateWalletUseCase
import com.financial.application.wallet.retrive.get.GetWalletUseCase
import com.financial.domain.wallet.WalletGateway
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration(proxyBeanMethods = false)
class WallerUseCaseConfig(
    val walletGateway: WalletGateway
) {

    @Bean
    fun createWalletUseCase(): CreateWalletUseCase {
        return CreateWalletUseCase(walletGateway)
    }

    @Bean
    fun getWalletUseCase(): GetWalletUseCase {
        return GetWalletUseCase(walletGateway)
    }
}
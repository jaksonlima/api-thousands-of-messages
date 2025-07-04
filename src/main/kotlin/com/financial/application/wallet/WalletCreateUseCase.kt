package com.financial.application.wallet

import com.financial.application.UseCase
import com.financial.domain.wallet.Wallet
import com.financial.domain.wallet.WalletGateway

class WalletCreateUseCase(
    private val walletGateway: WalletGateway
) : UseCase<WalletCreateUseCase.Input, WalletCreateUseCase.Output> {

    override fun execute(input: Input): Output {
        val wallet = Wallet.Companion.create(input.name())

        this.walletGateway.create(wallet)

        return StdOutput(wallet.id().value().toString())
    }

    interface Input {
        fun name(): String
    }

    interface Output {
        fun id(): String;
    }

    data class StdOutput(val id: String) : Output {
        override fun id() = id
    }

}
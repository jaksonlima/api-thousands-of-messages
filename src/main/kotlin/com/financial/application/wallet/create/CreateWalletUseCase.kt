package com.financial.application.wallet.create

import com.financial.application.UseCase
import com.financial.domain.wallet.Wallet
import com.financial.domain.wallet.WalletGateway

class CreateWalletUseCase(
    private val walletGateway: WalletGateway
) : UseCase<CreateWalletUseCase.Input, CreateWalletUseCase.Output> {

    override fun execute(input: Input): Output {
        val wallet = Wallet.create(input.name())

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
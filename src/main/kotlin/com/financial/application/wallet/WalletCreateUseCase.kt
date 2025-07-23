package com.financial.application.wallet

import com.financial.application.UseCase
import com.financial.domain.account.AccountID
import com.financial.domain.wallet.Wallet
import com.financial.domain.wallet.WalletGateway

class WalletCreateUseCase(
    private val walletGateway: WalletGateway
) : UseCase<WalletCreateUseCase.Input, WalletCreateUseCase.Output> {

    override fun execute(input: Input): Output {
        val accountId = AccountID.with(input.accountId())
        val wallet = Wallet.create(input.name(), accountId)

        this.walletGateway.create(wallet)

        return StdOutput(wallet.id().value().toString())
    }

    interface Input {
        fun name(): String
        fun accountId(): String
    }

    interface Output {
        fun id(): String;
    }

    data class StdOutput(val id: String) : Output {
        override fun id() = id
    }

}
package com.financial.infrastructure.wallet.models

import com.financial.application.wallet.WalletCreateUseCase

data class CreateRequest(
    val name: String,
    val accountId: String,
) : WalletCreateUseCase.Input {
    override fun name(): String = this.name
    override fun accountId(): String = this.accountId
}
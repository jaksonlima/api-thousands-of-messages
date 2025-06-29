package com.financial.infrastructure.wallet.models

import com.financial.application.wallet.create.CreateWalletUseCase

data class CreateRequest(
    val name: String,
) : CreateWalletUseCase.Input {
    override fun name(): String = this.name
}
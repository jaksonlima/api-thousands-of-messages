package com.financial.infrastructure.wallet.models

import com.financial.application.wallet.create.CreateWalletUseCase

data class CreateResponse(
    val id: String,
) {
    companion object {
        fun from(output: CreateWalletUseCase.Output): CreateResponse {
            return CreateResponse(output.id())
        }
    }
}
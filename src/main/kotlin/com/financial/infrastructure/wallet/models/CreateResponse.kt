package com.financial.infrastructure.wallet.models

import com.financial.application.wallet.WalletCreateUseCase

data class CreateResponse(
    val id: String,
) {
    companion object {
        fun from(output: WalletCreateUseCase.Output): CreateResponse {
            return CreateResponse(output.id())
        }
    }
}
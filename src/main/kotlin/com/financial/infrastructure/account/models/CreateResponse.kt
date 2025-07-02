package com.financial.infrastructure.account.models

import com.financial.application.account.AccountCreateUseCase

data class CreateResponse(val id: String) {
    companion object {
        fun from(output: AccountCreateUseCase.Output): CreateResponse {
            return CreateResponse(id = output.id())
        }
    }
}

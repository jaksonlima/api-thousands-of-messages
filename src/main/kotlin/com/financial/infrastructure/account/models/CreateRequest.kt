package com.financial.infrastructure.account.models

import com.financial.application.account.AccountCreateUseCase

data class CreateRequest(val name: String) : AccountCreateUseCase.Input {
    override fun name(): String = this.name
}

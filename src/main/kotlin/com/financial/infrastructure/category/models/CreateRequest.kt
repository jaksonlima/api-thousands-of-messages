package com.financial.infrastructure.category.models

import com.financial.application.category.CategoryCreateUseCase

data class CreateRequest(
    val name: String,
) : CategoryCreateUseCase.Input {
    override fun name(): String = this.name
}
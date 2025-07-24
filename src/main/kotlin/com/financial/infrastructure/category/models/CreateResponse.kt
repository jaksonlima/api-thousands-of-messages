package com.financial.infrastructure.category.models

import com.financial.application.category.CategoryCreateUseCase

data class CreateResponse(
    val id: String,
) {
    companion object {
        fun from(output: CategoryCreateUseCase.Output): CreateResponse {
            return CreateResponse(output.id())
        }
    }
}
package com.financial.application.category

import com.financial.application.UseCase
import com.financial.domain.category.Category
import com.financial.domain.category.CategoryGateway

class CategoryCreateUseCase(
    private val categoryGateway: CategoryGateway
) : UseCase<CategoryCreateUseCase.Input, CategoryCreateUseCase.Output> {

    override fun execute(input: Input): Output {
        val category = Category.create(input.name())

        val result = this.categoryGateway.create(category)

        return StdOutput(result.id().toString())
    }

    interface Input {
        fun name(): String
    }


    interface Output {
        fun id(): String
    }

    data class StdOutput(val id: String) : Output {
        override fun id(): String = this.id;
    }
}
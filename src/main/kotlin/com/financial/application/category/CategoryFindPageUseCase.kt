package com.financial.application.category

import com.financial.application.UseCase
import com.financial.domain.category.CategoryGateway
import com.financial.domain.pagination.Pagination
import java.time.Instant

class CategoryFindPageUseCase(
    private val categoryGateway: CategoryGateway
) : UseCase<CategoryFindPageUseCase.Input, Pagination<CategoryFindPageUseCase.Output>> {

    override fun execute(input: Input): Pagination<Output> {
        val result = this.categoryGateway.findPage(input.name(), input.page(), input.size())

        return result.map {
            StdOutput(
                id = it.id().toString(),
                name = it.name,
                createdAt = it.createdAt,
                updatedAt = it.updatedAt
            )
        }
    }

    interface Input {
        fun name(): String?
        fun page(): Int
        fun size(): Int
    }

    interface Output {
        fun id(): String
        fun name(): String
        fun createdAt(): Instant
        fun updatedAt(): Instant
    }

    data class StdOutput(
        val id: String,
        val name: String,
        val createdAt: Instant,
        val updatedAt: Instant
    ) : Output {
        override fun id(): String = this.id
        override fun name(): String = this.name
        override fun createdAt(): Instant = this.createdAt
        override fun updatedAt(): Instant = this.updatedAt
    }
}
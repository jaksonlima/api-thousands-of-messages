package com.financial.infrastructure.category

import com.financial.domain.category.Category
import com.financial.domain.category.CategoryGateway
import com.financial.domain.pagination.Pagination
import com.financial.infrastructure.category.persistence.CategoryJpaEntity
import com.financial.infrastructure.category.persistence.CategoryRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Component

@Component
class CategoryPostgresGateway(
    private val categoryRepository: CategoryRepository
) : CategoryGateway {

    override fun create(category: Category): Category {
        return this.categoryRepository.save(CategoryJpaEntity.from(category))
            .toDomain()
    }

    override fun findPage(
        name: String?,
        page: Int,
        size: Int
    ): Pagination<Category> {
        val pageable = PageRequest.of(page, size, Sort.by("name"))
        val pageResult = this.categoryRepository.findPageByName(name, pageable)
        return Pagination(
            currentPage = pageResult.number,
            totalPages = pageResult.totalPages,
            totalItems = pageResult.totalElements,
            items = pageResult.content.map { it.toDomain() }
        )
    }
}
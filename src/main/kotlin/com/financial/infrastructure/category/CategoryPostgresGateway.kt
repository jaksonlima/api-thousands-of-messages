package com.financial.infrastructure.category

import com.financial.domain.category.Category
import com.financial.domain.category.CategoryGateway
import com.financial.infrastructure.category.persistence.CategoryJpaEntity
import com.financial.infrastructure.category.persistence.CategoryRepository
import org.springframework.stereotype.Component

@Component
class CategoryPostgresGateway(
    private val categoryRepository: CategoryRepository
) : CategoryGateway {

    override fun create(category: Category): Category {
        return this.categoryRepository.save(CategoryJpaEntity.from(category))
            .toDomain()
    }
}
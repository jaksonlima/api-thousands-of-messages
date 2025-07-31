package com.financial.domain.category

import com.financial.domain.pagination.Pagination
import java.util.Optional

interface CategoryGateway {
    fun create(category: Category): Category
    fun findPage(name: String?, page: Int, size: Int): Pagination<Category>
    fun findById(categoryId: CategoryID): Optional<Category>
}
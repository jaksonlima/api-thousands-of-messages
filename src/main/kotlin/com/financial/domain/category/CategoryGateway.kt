package com.financial.domain.category

interface CategoryGateway {
    fun create(category: Category): Category
}
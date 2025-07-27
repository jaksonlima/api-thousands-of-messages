package com.financial.infrastructure.category.models

import com.financial.application.category.CategoryFindPageUseCase

data class FindPageRequest(
    val name: String?,
    val page: Int,
    val size: Int,
) : CategoryFindPageUseCase.Input {
    override fun name(): String? = this.name
    override fun page(): Int = this.page
    override fun size(): Int = this.size
}
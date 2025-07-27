package com.financial.domain.pagination

class Pagination<T>(
    val currentPage: Int,
    val totalPages: Int,
    val totalItems: Long,
    val items: List<T>
) {

    fun <R> map(map: (item: T) -> R): Pagination<R> {
        val result = items.map(map)
        return Pagination(
            currentPage = this.currentPage,
            totalPages = this.totalPages,
            totalItems = this.totalItems,
            items = result
        );
    }
}
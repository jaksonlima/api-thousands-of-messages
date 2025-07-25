package com.financial.domain.pagination

class Pagination<T>(
    val page: Int,
    val size: Int,
    val data: List<T>
) {

    fun <R> map(map: (item: T) -> R): Pagination<R> {
        val result = data.map(map)
        return Pagination(
            page = this.page,
            size = this.size,
            data = result
        );
    }
}
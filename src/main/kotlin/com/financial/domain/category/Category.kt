package com.financial.domain.category

import com.financial.domain.Aggregate
import java.time.Instant

class Category(
    id: CategoryID = CategoryID(),
    val name: String,
    val createdAt: Instant,
    val updatedAt: Instant,
    val deletedAt: Instant? = null
) : Aggregate<CategoryID>(id) {

    init {
        assertArgumentNotNull(id) { "'id' should not be null or empty." }
        assertArgumentNotEmpty(name) { "'name' should not be null or empty." }
        assertArgumentNotNull(createdAt) { "'createdAt' should not be null." }
        assertArgumentNotNull(updatedAt) { "'updatedAt' should not be null." }
        assertArgumentNull(deletedAt) { "'deletedAt' should be null." }
    }

    companion object {
        fun create(name: String): Category {
            val now = Instant.now()
            return Category(
                name = name,
                createdAt = now,
                updatedAt = now
            )
        }
    }
}

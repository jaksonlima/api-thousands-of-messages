package com.financial.infrastructure.category.persistence

import com.financial.domain.category.Category
import com.financial.domain.category.CategoryID
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.Instant

@Entity(name = "Category")
@Table(name = "category")
data class CategoryJpaEntity(
    @Id
    val id: String = "",

    @Column(name = "NAME", nullable = false)
    val name: String = "",

    @Column(name = "CREATED_AT", nullable = false)
    val createdAt: Instant = Instant.now(),

    @Column(name = "UPDATED_AT", nullable = false)
    var updatedAt: Instant = Instant.now(),
) {

    companion object {
        fun from(category: Category): CategoryJpaEntity {
            return CategoryJpaEntity(
                id = category.id().value().toString(),
                name = category.name,
                createdAt = category.createdAt,
                updatedAt = category.updatedAt,
            )
        }
    }

    fun toDomain(): Category {
        return Category(
            id = CategoryID.with(this.id),
            name = this.name,
            createdAt = this.createdAt,
            updatedAt = this.updatedAt,
        )
    }
}
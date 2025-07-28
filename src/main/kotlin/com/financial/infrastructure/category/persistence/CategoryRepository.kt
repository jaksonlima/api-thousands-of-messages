package com.financial.infrastructure.category.persistence

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository : JpaRepository<CategoryJpaEntity, String> {
    @Query(
        """
    SELECT c FROM Category c
    WHERE (:name IS NULL OR LOWER(c.name) LIKE LOWER(CONCAT('%', CAST(COALESCE(:name, '') AS string), '%')))
    """
    )
    fun findPageByName(@Param("name") name: String?, pageable: Pageable): Page<CategoryJpaEntity>
}
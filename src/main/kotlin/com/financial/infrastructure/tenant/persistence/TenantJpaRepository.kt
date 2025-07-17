package com.financial.infrastructure.tenant.persistence

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface TenantJpaRepository : JpaRepository<TenantJpaEntity, String> {
    fun findByAccountId(accountId: String): Optional<TenantJpaEntity>
}
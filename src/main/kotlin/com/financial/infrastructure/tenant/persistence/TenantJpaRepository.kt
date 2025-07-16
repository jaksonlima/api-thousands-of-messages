package com.financial.infrastructure.tenant.persistence

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TenantJpaRepository : JpaRepository<TenantJpaEntity, String> {
}
package com.financial.infrastructure.tenant.persistence

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.OffsetDateTime

@Entity(name = "Tenant")
@Table(name = "tenant")
class TenantJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: String? = null,

    @Column(name = "ACCOUNT_ID", nullable = false)
    val accountId: String,

    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    val createdAt: OffsetDateTime = OffsetDateTime.now(),

    @Column(name = "UPDATED_AT", nullable = false)
    var updatedAt: OffsetDateTime = OffsetDateTime.now()
)
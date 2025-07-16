package com.financial.infrastructure.tenant.persistence

import com.financial.domain.account.AccountID
import com.financial.domain.tenant.Tenant
import com.financial.domain.tenant.TenantID
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.Instant

@Entity(name = "Tenant")
@Table(name = "tenant")
data class TenantJpaEntity(
    @Id
    val id: String = "",

    @Column(name = "NAME", nullable = false)
    val name: String = "",

    @Column(name = "ACCOUNT_ID", nullable = false)
    val accountId: String = "",

    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    val createdAt: Instant = Instant.now(),

    @Column(name = "UPDATED_AT", nullable = false)
    var updatedAt: Instant = Instant.now()
) {
    companion object {
        fun from(tenant: Tenant): TenantJpaEntity {
            return TenantJpaEntity(
                id = tenant.id().value().toString(),
                name = tenant.name,
                accountId = tenant.accountId.value().toString(),
                createdAt = tenant.createdAt,
                updatedAt = tenant.updatedAt,
            )
        }
    }

    fun toDomain(): Tenant {
        return Tenant(
            id = TenantID.with(this.id),
            name = this.name,
            accountId = AccountID.with(this.accountId),
            createdAt = this.createdAt,
            updatedAt = this.updatedAt,
        )
    }
}
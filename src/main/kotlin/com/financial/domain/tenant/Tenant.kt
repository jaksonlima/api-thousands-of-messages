package com.financial.domain.tenant

import com.financial.domain.Aggregate
import com.financial.domain.account.AccountID
import java.time.Instant

class Tenant(
    id: TenantID,
    var name: String,
    val accountId: AccountID,
    val createdAt: Instant,
    val updatedAt: Instant,
) : Aggregate<TenantID>(id) {

    init {
        assertArgumentNotNull(id) { "'id' should not be null" }
        assertArgumentNotEmpty(this.name) { "'name' should not be null or empty " }
        assertArgumentNotNull(this.accountId) { "'accountId' should not be null " }
        assertArgumentNotNull(this.createdAt) { "'createdAt' should not be null" }
        assertArgumentNotNull(this.updatedAt) { "'updatedAt' should not be null" }

        changeName(this.name)
    }

    companion object {
        fun create(
            accountId: AccountID
        ): Tenant {
            val tenantId = TenantID()
            val name = tenantId.value().toString()
            val now = Instant.now()
            return Tenant(
                id = tenantId,
                name = name,
                accountId = accountId,
                createdAt = now,
                updatedAt = now
            )
        }

        fun with(
            id: TenantID = TenantID(),
            name: String,
            accountId: AccountID,
            createdAt: Instant,
            updatedAt: Instant,
        ): Tenant {
            return Tenant(
                id = id,
                name = name,
                accountId = accountId,
                createdAt = createdAt,
                updatedAt = updatedAt
            )
        }
    }

    fun changeName(name: String) {
        this.name = name.replace("-", "");
    }

}
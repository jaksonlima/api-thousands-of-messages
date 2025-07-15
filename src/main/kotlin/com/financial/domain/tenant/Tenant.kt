package com.financial.domain.tenant

import com.financial.domain.Aggregate
import com.financial.domain.account.AccountID
import java.time.Instant

class Tenant(
    id: TenantID = TenantID(),
    val accountId: AccountID,
    val createdAt: Instant,
    val updatedAt: Instant,
) : Aggregate<TenantID>(id) {

    init {
        assertArgumentNotNull(id) { "'id' should not be null" }
        assertArgumentNotNull(accountId) { "'accountId' should not be null " }
        assertArgumentNotNull(createdAt) { "'createdAt' should not be null" }
        assertArgumentNotNull(updatedAt) { "'updatedAt' should not be null" }
    }

    companion object {
        fun create(
            accountId: AccountID
        ): Tenant {
            val now = Instant.now()
            return Tenant(
                accountId = accountId,
                createdAt = now,
                updatedAt = now
            )
        }

        fun with(
            id: TenantID = TenantID(),
            accountId: AccountID,
            createdAt: Instant,
            updatedAt: Instant,
        ): Tenant {
            return Tenant(
                id = id,
                accountId = accountId,
                createdAt = createdAt,
                updatedAt = updatedAt
            )
        }
    }

}
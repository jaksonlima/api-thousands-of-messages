package com.financial.domain.account

import com.financial.domain.Aggregate
import java.time.Instant

class Account(
    id: AccountID = AccountID(),
    val name: String,
    val createdAt: Instant,
    val updatedAt: Instant,
) : Aggregate<AccountID>(id) {

    init {
        assertArgumentNotNull(id) { "'id' should not be null" }
        assertArgumentNotEmpty(name) { "'name' should not be null or empty" }
        assertArgumentNotNull(createdAt) { "'createdAt' should not be null" }
        assertArgumentNotNull(updatedAt) { "'updatedAt' should not be null" }
    }

    companion object {
        fun create(
            name: String,
        ): Account {
            val now = Instant.now()
            return Account(
                name = name,
                createdAt = now,
                updatedAt = now
            )
        }

        fun with(
            id: AccountID = AccountID(),
            name: String,
            createdAt: Instant,
            updatedAt: Instant,
            deletedAt: Instant? = null
        ): Account {
            return Account(
                id,
                name,
                createdAt,
                updatedAt
            )
        }
    }
}
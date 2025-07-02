package com.financial.domain.account

import com.financial.domain.Aggregate
import java.time.Instant

class Account(
    id: AccountID = AccountID(),
    val name: String,
    val createdAt: Instant,
    val updatedAt: Instant,
    val deletedAt: Instant? = null,
) : Aggregate<AccountID>(id) {

    init {
        assertArgumentNotNull(id) { "'id' should not be null" }
        assertArgumentNotEmpty(name) { "'name' should not be null or empty" }
        assertArgumentNotNull(createdAt) { "'createdAt' should not be null" }
        assertArgumentNotNull(updatedAt) { "'updatedAt' should not be null" }
        assertArgumentNull(deletedAt) { "'deletedAt' should be null" }
    }

    companion object {
        fun create(
            name: String,
        ): Account {
            val now = Instant.now()
            val account = Account(
                name = name,
                createdAt = now,
                updatedAt = now
            )

            account.createEvent();

            return account
        }

        fun with(
            id: AccountID = AccountID(),
            name: String,
            createdAt: Instant,
            updatedAt: Instant,
            deletedAt: Instant
        ): Account {
            return Account(
                id = id,
                name = name,
                createdAt = createdAt,
                updatedAt = updatedAt,
                deletedAt = deletedAt
            )
        }
    }

    private fun createEvent() {
        val accountCreateEvent = AccountCreateEvent(
            accountId = this.id().value().toString(),
            createdAt = this.createdAt,
            occurredOn = Instant.now()
        )

        this.registryEvent(accountCreateEvent)
    }
}
package com.financial.domain.wallet

import com.financial.domain.Aggregate
import java.time.Instant

class Wallet(
    id: WalletID = WalletID(),
    val name: String,
    val active: Boolean,
    val createdAt: Instant,
    val updatedAt: Instant,
    val deletedAt: Instant? = null
) : Aggregate<WalletID>(id) {

    init {
        assertArgumentNotNull(id) { "'id' should not be null" }
        assertArgumentNotEmpty(name) { "'name' should not be null or empty" }
        assertArgumentNotNull(active) { "'active' should not be null" }
        assertArgumentNotNull(createdAt) { "'createdAt' should not be null" }
        assertArgumentNotNull(updatedAt) { "'updatedAt' should not be null" }
        assertArgumentNull(deletedAt) { "'deletedAt' should be null" }
    }

    companion object {
        fun create(
            name: String,
        ): Wallet {
            val now = Instant.now()
            return Wallet(
                name = name,
                active = true,
                createdAt = now,
                updatedAt = now
            )
        }

        fun with(
            id: WalletID = WalletID(),
            name: String,
            active: Boolean,
            createdAt: Instant,
            updatedAt: Instant,
            deletedAt: Instant? = null
        ): Wallet {
            return Wallet(
                id,
                name,
                active,
                createdAt,
                updatedAt,
                deletedAt
            )
        }
    }
}
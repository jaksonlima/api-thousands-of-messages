package com.financial.infrastructure.wallet.persistence

import com.financial.domain.account.AccountID
import com.financial.domain.wallet.Wallet
import com.financial.domain.wallet.WalletID
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.Instant


@Entity(name = "Wallet")
@Table(name = "wallet")
data class WalletJpaEntity(
    @Id
    val id: String = "",

    @Column(name = "NAME", nullable = false)
    val name: String = "",

    @Column(name = "ACTIVE", nullable = false)
    val active: Boolean = true,

    @Column(name = "ACCOUNT_ID", nullable = false)
    val accountId: String = "",

    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    val createdAt: Instant = Instant.now(),

    @Column(name = "UPDATED_AT", nullable = false)
    var updatedAt: Instant = Instant.now(),

    @Column(name = "DELETED_AT", nullable = true)
    var deletedAt: Instant? = null
) {
    companion object {
        fun from(wallet: Wallet): WalletJpaEntity {
            return WalletJpaEntity(
                id = wallet.id().value().toString(),
                name = wallet.name,
                active = wallet.active,
                accountId = wallet.accountId.toString(),
                createdAt = wallet.createdAt,
                updatedAt = wallet.updatedAt,
                deletedAt = wallet.deletedAt
            )
        }
    }

    fun toDomain(): Wallet {
        return Wallet(
            id = WalletID.with(this.id),
            name = this.name,
            active = this.active,
            accountId = AccountID.with(this.accountId),
            createdAt = this.createdAt,
            updatedAt = this.updatedAt,
            deletedAt = this.deletedAt
        )
    }
}
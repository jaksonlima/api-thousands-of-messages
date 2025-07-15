package com.financial.infrastructure.account.persistence

import com.financial.domain.account.Account
import com.financial.domain.account.AccountID
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.Instant

@Entity(name = "Account")
@Table(name = "account", schema = "public")
data class AccountJpaEntity(
    @Id
    val id: String = "",

    @Column(name = "NAME", nullable = false)
    val name: String = "",

    @Column(name = "CREATED_AT", nullable = false)
    val createdAt: Instant = Instant.now(),

    @Column(name = "UPDATED_AT", nullable = false)
    var updatedAt: Instant = Instant.now(),

    @Column(name = "DELETED_AT", nullable = true)
    var deletedAt: Instant? = null
) {

    companion object {
        fun from(account: Account): AccountJpaEntity {
            return AccountJpaEntity(
                id = account.id().value().toString(),
                name = account.name,
                createdAt = account.createdAt,
                updatedAt = account.updatedAt,
                deletedAt = account.deletedAt
            )
        }
    }

    fun toDomain(): Account {
        return Account(
            id = AccountID.with(this.id),
            name = this.name,
            createdAt = this.createdAt,
            updatedAt = this.updatedAt,
            deletedAt = this.deletedAt
        )
    }
}
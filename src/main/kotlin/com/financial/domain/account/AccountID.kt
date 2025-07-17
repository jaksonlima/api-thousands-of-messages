package com.financial.domain.account

import com.financial.domain.Identifier
import java.util.*

data class AccountID(private val id: UUID = UUID.randomUUID()) : Identifier<UUID> {

    override fun value(): UUID = this.id

    companion object {
        fun with(aId: String): AccountID = AccountID(UUID.fromString(aId))
    }

    override fun toString(): String {
        return this.value().toString()
    }
}

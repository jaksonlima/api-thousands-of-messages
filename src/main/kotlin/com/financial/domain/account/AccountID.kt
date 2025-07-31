package com.financial.domain.account

import com.financial.domain.Identifier
import java.util.*

data class AccountID(override val id: UUID = UUID.randomUUID()) : Identifier<UUID>() {

    companion object {
        fun with(aId: String): AccountID = AccountID(UUID.fromString(aId))
    }

    override fun toString(): String {
        return id.toString()
    }
}

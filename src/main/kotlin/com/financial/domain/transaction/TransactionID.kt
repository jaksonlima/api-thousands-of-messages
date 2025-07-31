package com.financial.domain.transaction

import com.financial.domain.Identifier
import java.util.*

data class TransactionID(override val id: UUID = UUID.randomUUID()) : Identifier<UUID>() {

    companion object {
        fun with(aId: String): TransactionID = TransactionID(UUID.fromString(aId))
    }

    override fun toString(): String {
        return id.toString()
    }

}

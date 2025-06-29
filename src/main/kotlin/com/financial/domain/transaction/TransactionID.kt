package com.financial.domain.transaction

import com.financial.domain.Identifier
import java.util.*

data class TransactionID(private val id: UUID = UUID.randomUUID()) : Identifier<UUID> {
    override fun value(): UUID {
        return this.id
    }
}

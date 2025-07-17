package com.financial.domain.tenant

import com.financial.domain.Identifier
import java.util.*

data class TenantID(private val id: UUID = UUID.randomUUID()) : Identifier<UUID> {

    override fun value(): UUID = this.id

    companion object {
        fun with(aId: String): TenantID = TenantID(UUID.fromString(aId))
    }

    override fun toString(): String {
        return value().toString()
    }
}

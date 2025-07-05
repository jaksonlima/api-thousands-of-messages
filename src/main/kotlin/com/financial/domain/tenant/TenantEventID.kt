package com.financial.domain.tenant

import com.financial.domain.Identifier
import java.util.*

data class TenantEventID(private val id: UUID = UUID.randomUUID()) : Identifier<UUID> {

    override fun value(): UUID = this.id

    companion object {
        fun with(aId: String): TenantEventID = TenantEventID(UUID.fromString(aId))
    }
}

package com.financial.domain.tenant

import com.financial.domain.Identifier
import java.util.*

data class TenantEventID(override val id: UUID = UUID.randomUUID()) : Identifier<UUID>() {

    companion object {
        fun with(aId: String): TenantEventID = TenantEventID(UUID.fromString(aId))
    }

    override fun toString(): String {
        return id.toString()
    }
}

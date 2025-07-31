package com.financial.domain.tenant

import com.financial.domain.Identifier
import java.util.*

data class TenantID(override val id: UUID = UUID.randomUUID()) : Identifier<UUID>() {

    companion object {
        fun with(aId: String): TenantID = TenantID(UUID.fromString(aId))
    }

    override fun toString(): String {
        return id.toString()
    }
}

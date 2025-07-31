package com.financial.domain.wallet

import com.financial.domain.Identifier
import java.util.*

data class WalletID(override val id: UUID = UUID.randomUUID()) : Identifier<UUID>() {

    companion object {
        fun with(aId: String): WalletID = WalletID(UUID.fromString(aId))
    }

    override fun toString(): String {
        return id.toString()
    }
}

package com.financial.infrastructure.wallet

import com.financial.domain.wallet.Wallet
import com.financial.domain.wallet.WalletGateway
import com.financial.domain.wallet.WalletID
import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.ConcurrentHashMap

@Component
class InMemoryWalletGateway : WalletGateway {
    private val db: MutableMap<WalletID, Wallet> = ConcurrentHashMap()

    override fun create(wallet: Wallet): Wallet {
        db.put(wallet.id(), wallet)
        return wallet
    }

    override fun findById(walletID: WalletID): Optional<Wallet> {
        return Optional.ofNullable(db[walletID])
    }
}
package com.financial.infrastructure.wallet

import com.financial.domain.wallet.Wallet
import com.financial.domain.wallet.WalletGateway
import com.financial.domain.wallet.WalletID
import com.financial.infrastructure.wallet.persistence.WalletJpaEntity
import com.financial.infrastructure.wallet.persistence.WalletJpaRepository
import org.springframework.stereotype.Component
import java.util.*

@Component
class WalletPostgresGateway(
    private val walletJpaRepository: WalletJpaRepository
) : WalletGateway {

    override fun create(wallet: Wallet): Wallet {
        return this.walletJpaRepository.save(WalletJpaEntity.from(wallet))
            .toDomain()
    }

    override fun findById(walletId: WalletID): Optional<Wallet> {
        return this.walletJpaRepository.findById(walletId.toString())
            .map(WalletJpaEntity::toDomain)
    }
}
package com.financial.infrastructure.wallet.persistence

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface WalletJpaRepository : JpaRepository<WalletJpaEntity, String> {
}
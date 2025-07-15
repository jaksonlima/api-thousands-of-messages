package com.financial.infrastructure.account.persistence

import com.financial.domain.account.Account
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*


@Repository
interface AccountRepository : JpaRepository<AccountJpaEntity, String> {
    fun getByIdAndDeletedAtIsNull(id: String): Optional<AccountJpaEntity>
}
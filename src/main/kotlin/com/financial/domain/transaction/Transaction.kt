package com.financial.domain.transaction

import com.financial.domain.Aggregate
import com.financial.domain.category.CategoryID
import com.financial.domain.wallet.WalletID
import java.time.Instant

class Transaction(
    id: TransactionID = TransactionID(),
    val amountType: AmountType,
    val money: Money,
    val name: String,
    val walletId: WalletID,
    val categoryId: CategoryID,
    val createdAt: Instant,
    val updatedAt: Instant,
) : Aggregate<TransactionID>(id) {

    init {
        assertArgumentNotNull(id) { "'id' should not be null or empty." }
        assertArgumentNotNull(amountType) { "'amountType' should not be null." }
        assertArgumentNotNull(money) { "'money' should not be null." }
        assertArgumentNotEmpty(name) { "'name' should not be null or empty." }
        assertArgumentNotNull(walletId) { "'walletId' should not be null." }
        assertArgumentNotNull(categoryId) { "'categoryId' should not be null." }
        assertArgumentNotNull(createdAt) { "'createdAt' should not be null." }
        assertArgumentNotNull(updatedAt) { "'updatedAt' should not be null." }
    }

    companion object {
        fun create(
            amountType: AmountType,
            money: Money,
            name: String,
            walletId: WalletID,
            categoryId: CategoryID,
        ): Transaction {
            val now = Instant.now()
            return Transaction(
                amountType = amountType,
                money = money,
                name = name,
                walletId = walletId,
                categoryId = categoryId,
                createdAt = now,
                updatedAt = now
            )
        }
    }

}
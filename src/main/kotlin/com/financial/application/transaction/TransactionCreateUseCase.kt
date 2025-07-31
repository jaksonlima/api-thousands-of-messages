package com.financial.application.transaction

import com.financial.application.UseCase
import com.financial.domain.category.Category
import com.financial.domain.category.CategoryGateway
import com.financial.domain.category.CategoryID
import com.financial.domain.expections.NotFoundException
import com.financial.domain.transaction.AmountType
import com.financial.domain.transaction.Money
import com.financial.domain.transaction.Transaction
import com.financial.domain.transaction.TransactionGateway
import com.financial.domain.wallet.Wallet
import com.financial.domain.wallet.WalletGateway
import com.financial.domain.wallet.WalletID

class TransactionCreateUseCase(
    private val transactionGateway: TransactionGateway,
    private val categoryGateway: CategoryGateway,
    private val walletGateway: WalletGateway,
) : UseCase<TransactionCreateUseCase.Input, TransactionCreateUseCase.Output> {

    override fun execute(input: Input): Output {
        val inputAmountType = input.amountType()
        val inputAmount = input.amount()
        val inputName = input.name()

        val walletId = WalletID.with(input.walletId())
        val categoryId = CategoryID.with(input.categoryId())

        val amountType = AmountType.from(inputAmountType)
        val money = Money.create(inputAmount)

        val category = this.categoryGateway.findById(categoryId)
            .orElseThrow { NotFoundException.with(Category::class, categoryId) }

        val wallet = this.walletGateway.findById(walletId)
            .orElseThrow { NotFoundException.with(Wallet::class, walletId) }

        val transaction = Transaction.create(
            amountType = amountType,
            money = money,
            name = inputName,
            walletId = wallet.id(),
            categoryId = category.id()
        )

        return StdOutput(transaction.id().toString())
    }

    interface Input {
        fun amountType(): String
        fun amount(): Double
        fun name(): String
        fun walletId(): String
        fun categoryId(): String
    }

    interface Output {
        fun id(): String
    }

    data class StdOutput(val id: String) : Output {
        override fun id(): String = this.id
    }
}
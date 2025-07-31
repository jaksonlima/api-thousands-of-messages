package com.financial.domain.transaction

import com.financial.domain.UnitTest
import com.financial.domain.category.CategoryID
import com.financial.domain.expections.DomainException
import com.financial.domain.wallet.WalletID
import org.junit.jupiter.api.Test
import kotlin.test.*

class TransactionTest : UnitTest() {

    @Test
    fun givenValidParams_whenCreatingTransaction_shouldCreated() {
        //given
        val expectedAmountType = AmountType.DEBIT
        val expectedMoney = Money.create(20.0)
        val expectedDescription = "fuel"
        val expectedWalletId = WalletID()
        val expectedCategoryId = CategoryID()

        //when
        val result = Transaction.create(
            amountType = expectedAmountType,
            money = expectedMoney,
            name = expectedDescription,
            walletId = expectedWalletId,
            categoryId = expectedCategoryId
        )
        val toString = result.id().toString()

        //then
        assertNotNull(result.id())
        assertEquals(expectedAmountType, result.amountType)
        assertEquals(expectedMoney, result.money)
        assertEquals(expectedDescription, result.name)
        assertEquals(expectedWalletId, result.walletId)
        assertEquals(expectedCategoryId, result.categoryId)
        assertNotNull(result.createdAt)
        assertNotNull(result.updatedAt)
    }

    @Test
    fun givenInvalidParams_whenCreatingTransaction_shouldReturnError() {
        //given
        val expectedAmountType = AmountType.DEBIT
        val expectedMoney = Money.create(20.0)
        val expectedDescription = ""
        val expectedWalletId = WalletID()
        val expectedCategoryId = CategoryID()

        //when

        val actualError = assertFailsWith(DomainException::class) {
            Transaction.create(
                amountType = expectedAmountType,
                money = expectedMoney,
                name = expectedDescription,
                walletId = expectedWalletId,
                categoryId = expectedCategoryId
            )
        }

        //then
        assertTrue(actualError.errors.isNotEmpty())
    }
}
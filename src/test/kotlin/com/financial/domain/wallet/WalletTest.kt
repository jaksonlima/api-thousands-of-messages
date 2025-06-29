package com.financial.domain.wallet

import com.financial.domain.UnitTest
import com.financial.domain.expections.DomainException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class WalletTest : UnitTest() {

    @Test
    fun givenValidParams_whenCreate_shouldReturnValidIt() {
        //given
        val expectedName = "jack"

        //when
        val wallet = Wallet.create(expectedName)

        //then
        assertNotNull(wallet.id())
        assertEquals(expectedName, wallet.name)
        assertTrue(wallet.active)
        assertNotNull(wallet.createdAt)
        assertNotNull(wallet.updatedAt)
        assertNull(wallet.deletedAt)
    }

    @Test
    fun givenInvalidParams_whenCreate_shouldReturnError() {
        //given
        val expectedName = ""

        //when
        val actualError = Assertions.assertThrows(DomainException::class.java)
        { Wallet.create(expectedName) }

        //then
        assertTrue(actualError.errors.isNotEmpty())
    }

}
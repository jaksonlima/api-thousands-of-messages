package com.financial.domain.account

import com.financial.domain.UnitTest
import com.financial.domain.expections.DomainException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class AccountTest : UnitTest() {

    @Test
    fun givenValidParams_whenCreate_shouldReturnValidIt() {
        //given
        val expectedName = "jack"

        //when
        val account = Account.create(expectedName)

        //then
        assertNotNull(account.id())
        assertTrue(account.domainEvents().isNotEmpty())
        assertEquals(expectedName, account.name)
        assertNotNull(account.createdAt)
        assertNotNull(account.updatedAt)
        assertNull(account.deletedAt)
    }

    @Test
    fun givenInvalidParams_whenCreate_shouldReturnError() {
        //given
        val expectedName = ""

        //when
        val actualError = Assertions.assertThrows(DomainException::class.java)
        { Account.create(expectedName) }

        //then
        assertTrue(actualError.errors.isNotEmpty())
    }

}
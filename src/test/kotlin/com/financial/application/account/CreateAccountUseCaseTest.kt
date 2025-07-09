package com.financial.application.account

import com.financial.application.UseCaseTest
import com.financial.domain.account.Account
import com.financial.domain.account.AccountGateway
import com.financial.domain.expections.DomainException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class CreateAccountUseCaseTest : UseCaseTest() {

    @InjectMocks
    lateinit var useCase: AccountCreateUseCase;

    @Mock
    lateinit var gateway: AccountGateway

    @Test
    fun givenValidParam_whenCallsCreateAccount_shouldReturnNewAccount() {
        //
        val expectedName = "jack"

        `when`(this.gateway.create(any()))
            .thenAnswer { it.arguments[0] }

        //when
        val result = this.useCase.execute(Input(expectedName))

        //then
        val captor = argumentCaptor<Account>()

        verify(this.gateway, times(1)).create(captor.capture());

        assertNotNull(result.id())
        assertTrue(captor.firstValue.domainEvents().isNotEmpty())
        assertEquals(expectedName, captor.firstValue.name)
    }


    @Test
    fun givenInvalidParam_whenCallsCreateAccount_shouldReturnError() {
        //
        val expectedName = ""

        //when
        val actualError = assertFailsWith(DomainException::class) {
            this.useCase.execute(Input(expectedName))
        }

        //then
        assertTrue(actualError.errors.isNotEmpty())
    }


    data class Input(val name: String) : AccountCreateUseCase.Input {
        override fun name(): String = this.name
    }
}
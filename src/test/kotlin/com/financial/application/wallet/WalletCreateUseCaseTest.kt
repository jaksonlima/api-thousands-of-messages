package com.financial.application.wallet

import com.financial.application.UseCaseTest
import com.financial.domain.account.AccountID
import com.financial.domain.expections.DomainException
import com.financial.domain.wallet.Wallet
import com.financial.domain.wallet.WalletGateway
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class WalletCreateUseCaseTest : UseCaseTest() {

    @InjectMocks
    lateinit var useCase: WalletCreateUseCase;

    @Mock
    lateinit var gateway: WalletGateway

    @Test
    fun givenValidParam_whenCallsCreateWallet_shouldReturnNewWallet() {
        //
        val expectedName = "jack"
        val expectedAccountId = AccountID().toString()

        Mockito.`when`(this.gateway.create(any()))
            .thenAnswer { it.arguments[0] }

        //when
        val result = this.useCase.execute(Input(expectedName, expectedAccountId))

        //then
        val captor = argumentCaptor<Wallet>()

        verify(this.gateway, times(1)).create(captor.capture());

        assertNotNull(result.id())
        assertEquals(expectedName, captor.firstValue.name)
        assertEquals(expectedAccountId, captor.firstValue.accountId.toString())
    }


    @Test
    fun givenInvalidParam_whenCallsCreateWallet_shouldReturnError() {
        //
        val expectedName = ""
        val expectedAccountId = AccountID().toString()

        //when
        val actualError = assertFailsWith(DomainException::class) {
            this.useCase.execute(Input(expectedName, expectedAccountId))
        }

        //then
        assertTrue(actualError.errors.isNotEmpty())
    }


    data class Input(val name: String, val accountId: String) : WalletCreateUseCase.Input {
        override fun name(): String = this.name
        override fun accountId(): String = this.accountId
    }
}
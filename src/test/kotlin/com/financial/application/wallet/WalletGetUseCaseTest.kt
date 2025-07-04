package com.financial.application.wallet

import com.financial.application.UseCaseTest
import com.financial.domain.Fixture
import com.financial.domain.expections.NotFoundException
import com.financial.domain.wallet.WalletGateway
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.kotlin.any
import java.util.Optional
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull

class WalletGetUseCaseTest : UseCaseTest() {

    @InjectMocks
    lateinit var walletGetUseCase: WalletGetUseCase;

    @Mock
    lateinit var gateway: WalletGateway;

    @Test
    fun givenValidId_whenCallsFindById_shouldReturnWallet() {
        //given
        val expectedWallet = Fixture.john()
        val id = expectedWallet.id()

        //when
        Mockito.`when`(this.gateway.findById(any()))
            .thenReturn(Optional.of(expectedWallet))

        val resultWallet = this.walletGetUseCase.execute(expectedWallet.id().value().toString())

        //then
        assertEquals(id.value().toString(), resultWallet.id())
        assertEquals(expectedWallet.name, resultWallet.name())
        assertEquals(expectedWallet.active, resultWallet.active())
        assertEquals(expectedWallet.createdAt, resultWallet.createdAt())
        assertEquals(expectedWallet.updatedAt, resultWallet.updatedAt())
        assertEquals(expectedWallet.deletedAt, resultWallet.deletedAt())
    }

    @Test
    fun givenInvalidId_whenCallsFindById_shouldReturnNotFound() {
        //given
        val expectedWallet = Fixture.john()

        //when
        val actualError = assertFailsWith(NotFoundException::class) {
            this.walletGetUseCase.execute(expectedWallet.id().value().toString())
        }

        //then
        assertNotNull(actualError.message)
    }

}
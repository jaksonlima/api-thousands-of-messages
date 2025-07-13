package com.financial.application.account

import com.financial.application.UseCaseTest
import com.financial.domain.account.Account
import com.financial.domain.account.AccountGateway
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class AccountGetUseCaseTest : UseCaseTest() {

    @InjectMocks
    lateinit var useCase: AccountGetUseCase;

    @Mock
    lateinit var gateway: AccountGateway

    @Test
    fun givenValidParam_whenCallsGetAccount_shouldReturnFoundAccount() {
        //
        val expectedAccount = Account.create("jack")

        `when`(this.gateway.getByIdAndDeletedAtIsNull(any()))
            .thenReturn(expectedAccount)

        //when
        val result = this.useCase.execute(expectedAccount.id().value().toString())

        //then
        assertTrue(result.isPresent)
        assertNotNull(result.get().id())
        assertEquals(expectedAccount.id().value().toString(), result.get().id())
    }
}
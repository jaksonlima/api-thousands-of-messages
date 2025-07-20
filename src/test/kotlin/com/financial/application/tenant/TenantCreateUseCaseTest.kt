package com.financial.application.tenant

import com.financial.application.UseCaseTest
import com.financial.domain.account.Account
import com.financial.domain.tenant.Tenant
import com.financial.domain.tenant.TenantGateway
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
import java.util.*

class TenantCreateUseCaseTest : UseCaseTest() {

    @InjectMocks
    private lateinit var useCase: TenantCreateUseCase

    @Mock
    private lateinit var tenantGateway: TenantGateway

    @Test
    fun givenValidParam_whenCallsCreateTenantEvent_shouldReturnNewTenant() {
        //
        val expectedAccount = Account.create("jack")
        val expectedAccountId = expectedAccount.id().toString()

        //when
        `when`(this.tenantGateway.findByAccountId(any()))
            .thenReturn(Optional.empty())

        `when`(this.tenantGateway.create(any()))
            .thenAnswer { it.arguments[0] }

        val result = this.useCase.execute(expectedAccountId)

        //then
        val captor = argumentCaptor<Tenant>()

        verify(this.tenantGateway, times(1)).create(captor.capture());

        val tenantResult = captor.firstValue

        assertNotNull(result.id())
        assertEquals(expectedAccountId, tenantResult.accountId.value().toString())
        assertNotNull(tenantResult.createdAt)
        assertNotNull(tenantResult.updatedAt)
    }

    @Test
    fun givenValidParam_whenCallsExistsTenantEvent_shouldReturnTenant() {
        //
        val expectedAccount = Account.create("jack")
        val expectedAccountId = expectedAccount.id().toString()
        val tenant = Tenant.create(expectedAccount.id())

        //when
        `when`(this.tenantGateway.findByAccountId(any()))
            .thenReturn(Optional.of(tenant))

        `when`(this.tenantGateway.createSchema(any()))
            .thenAnswer { it.arguments[0] }

        val result = this.useCase.execute(expectedAccountId)

        //then
        val captor = argumentCaptor<Tenant>()

        verify(this.tenantGateway, times(1)).createSchema(captor.capture());

        val tenantResult = captor.firstValue

        assertNotNull(result.id())
        assertEquals(expectedAccountId, tenantResult.accountId.value().toString())
        assertNotNull(tenantResult.createdAt)
        assertNotNull(tenantResult.updatedAt)
    }

}
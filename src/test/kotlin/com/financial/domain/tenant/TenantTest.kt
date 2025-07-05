package com.financial.domain.tenant

import com.financial.domain.UnitTest
import com.financial.domain.account.AccountID
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class TenantTest : UnitTest() {

    @Test
    fun givenValidParams_whenCreate_shouldReturnValidIt() {
        //given
        val expectedAccountId = AccountID()

        //when
        val tenant = Tenant.create(expectedAccountId)

        //then
        assertNotNull(tenant.id())
        assertEquals(expectedAccountId, tenant.accountId)
        assertNotNull(tenant.createdAt)
        assertNotNull(tenant.updatedAt)
    }

    @Test
    fun givenValidParams_whenCreateAndEvent_shouldReturnValidIt() {
        //given
        val expectedAccountId = AccountID()

        //when
        val tenant = Tenant.create(expectedAccountId)
        val event = tenant.createTenantEventProcessing()

        //then
        assertNotNull(tenant.id())
        assertEquals(expectedAccountId, tenant.accountId)
        assertNotNull(tenant.createdAt)
        assertNotNull(tenant.updatedAt)

        assertEquals(tenant.id(), event.tenantId)
        assertEquals(EventType.PROCESSING, event.eventType)
        assertNotNull(event.createdAt)
    }

}
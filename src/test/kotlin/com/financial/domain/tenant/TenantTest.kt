package com.financial.domain.tenant

import com.financial.domain.UnitTest
import com.financial.domain.account.AccountID
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

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
        assertTrue(tenant.tenantEvents().isEmpty())
        assertNotNull(tenant.createdAt)
        assertNotNull(tenant.updatedAt)
    }

    @Test
    fun givenValidParams_whenCreateAndEventCreated_shouldReturnValidIt() {
        //given
        val expectedAccountId = AccountID()

        //when
        val tenant = Tenant.create(expectedAccountId)
            .createTenantEventCreated()

        val tenantEvent = tenant.tenantEvents().first()

        //then
        assertNotNull(tenant.id())
        assertEquals(expectedAccountId, tenant.accountId)
        assertTrue(tenant.tenantEvents().isNotEmpty())
        assertNotNull(tenant.createdAt)
        assertNotNull(tenant.updatedAt)


        assertEquals(tenant.id(), tenantEvent.tenantId)
        assertEquals(EventType.CREATED, tenantEvent.eventType)
        assertTrue(tenantEvent.content.isNotEmpty())
        assertNotNull(tenantEvent.createdAt)
    }

    @Test
    fun givenValidParams_whenCreateAndEventProcessing_shouldReturnValidIt() {
        //given
        val expectedAccountId = AccountID()

        //when
        val tenant = Tenant.create(expectedAccountId)
            .createTenantEventProcessing()

        val tenantEvent = tenant.tenantEvents().first()

        //then
        assertNotNull(tenant.id())
        assertEquals(expectedAccountId, tenant.accountId)
        assertTrue(tenant.tenantEvents().isNotEmpty())
        assertNotNull(tenant.createdAt)
        assertNotNull(tenant.updatedAt)


        assertEquals(tenant.id(), tenantEvent.tenantId)
        assertEquals(EventType.PROCESSING, tenantEvent.eventType)
        assertTrue(tenantEvent.content.isNotEmpty())
        assertNotNull(tenantEvent.createdAt)
    }

}
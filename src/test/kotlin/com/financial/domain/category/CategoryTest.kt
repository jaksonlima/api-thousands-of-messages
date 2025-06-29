package com.financial.domain.category

import com.financial.domain.UnitTest
import com.financial.domain.expections.DomainException
import org.junit.jupiter.api.Test
import kotlin.test.*


class CategoryTest : UnitTest() {

    @Test
    fun givenValidParams_whenCreate_shouldReturnValidIt() {
        //given
        val expectedName = "car"

        //when
        val category = Category.create(expectedName)

        //then
        assertNotNull(category.id())
        assertEquals(expectedName, category.name)
        assertNotNull(category.createdAt)
        assertNotNull(category.updatedAt)
        assertNull(category.deletedAt)
    }

    @Test
    fun givenInvalidParams_whenCreate_shouldReturnError() {
        //given
        val expectedName = ""

        //when
        val actualError = assertFailsWith(DomainException::class) {
            Category.create(expectedName)
        }

        //then
        assertTrue(actualError.errors.isNotEmpty())
    }

}
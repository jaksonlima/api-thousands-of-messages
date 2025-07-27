package com.financial.application.category

import com.financial.application.UseCaseTest
import com.financial.domain.category.Category
import com.financial.domain.category.CategoryGateway
import com.financial.domain.pagination.Pagination
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.kotlin.eq
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class CategoryFindPageUseCaseTest : UseCaseTest() {

    @InjectMocks
    private lateinit var useCase: CategoryFindPageUseCase

    @Mock
    private lateinit var gateway: CategoryGateway

    @Test
    fun given_when_then() {
        //given
        val currentPage = 0
        val totalPages = 0
        val totalItems = 1L

        val expectedPage = 0
        val expectedSize = 1
        val expectedName = "test"
        val expectedInput = Input(expectedName, expectedPage, expectedSize)

        val expectedOutputName = "test"
        val expectedCategory = Category.create(expectedOutputName)

        val expectedPagination = Pagination(
            currentPage = currentPage,
            totalPages = totalPages,
            totalItems = totalItems,
            items = listOf(expectedCategory)
        )

        //when
        `when`(
            this.gateway.findPage(
                eq(expectedName),
                eq(expectedPage),
                eq(expectedSize)
            )
        )
            .thenReturn(expectedPagination)

        val result = this.useCase.execute(expectedInput)
        //then

        assertEquals(currentPage, result.currentPage)
        assertEquals(totalPages, result.totalPages)
        assertEquals(totalItems, result.totalItems)

        val actualItem = result.items[0]

        assertNotNull(actualItem)
        assertEquals(expectedCategory.id().toString(), actualItem.id())
        assertEquals(expectedCategory.name, actualItem.name())
        assertEquals(expectedCategory.createdAt, actualItem.createdAt())
        assertEquals(expectedCategory.updatedAt, actualItem.updatedAt())
    }

    data class Input(
        val name: String,
        val page: Int,
        val size: Int
    ) : CategoryFindPageUseCase.Input {
        override fun name(): String = this.name
        override fun page(): Int = this.page
        override fun size(): Int = this.size
    }

}
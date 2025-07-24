package com.financial.infrastructure.api.controllers

import com.financial.application.category.CategoryCreateUseCase
import com.financial.infrastructure.api.CategoryAPI
import com.financial.infrastructure.category.models.CreateRequest
import com.financial.infrastructure.category.models.CreateResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
class CategoryController(
    private val categoryCreateUseCase: CategoryCreateUseCase,
) : CategoryAPI {

    override fun create(category: CreateRequest): ResponseEntity<CreateResponse> {
        val result = this.categoryCreateUseCase.execute(category)

        return ResponseEntity.created(URI.create("/categories/${result.id()}"))
            .body(CreateResponse.from(result))
    }

}
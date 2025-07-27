package com.financial.infrastructure.api

import com.financial.application.category.CategoryFindPageUseCase.Output
import com.financial.domain.pagination.Pagination
import com.financial.infrastructure.category.models.CreateRequest
import com.financial.infrastructure.category.models.CreateResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping(value = ["categories"])
@Tag(name = "Category")
interface CategoryAPI {

    @PutMapping(
        value = ["/"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    @Operation(summary = "Create Category")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Created Category"),
            ApiResponse(responseCode = "422", description = "A validation error was observed"),
            ApiResponse(responseCode = "500", description = "An unpredictable error was observed"),
        ]
    )
    fun create(@RequestBody category: CreateRequest): ResponseEntity<CreateResponse>

    @GetMapping(
        value = ["/page"], produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    @Operation(summary = "Find Page Categories by filters")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Retrieved Account"),
            ApiResponse(responseCode = "404", description = "Not Found Account id"),
            ApiResponse(responseCode = "500", description = "An unpredictable error was observed"),
        ]
    )
    fun findPage(
        @RequestParam(name = "name", required = false) name: String?,
        @RequestParam(name = "page", defaultValue = "0") page: Int,
        @RequestParam(name = "size", defaultValue = "10") size: Int,
    ): ResponseEntity<Pagination<Output>>
}
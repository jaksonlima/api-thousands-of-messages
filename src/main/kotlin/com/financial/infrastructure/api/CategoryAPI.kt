package com.financial.infrastructure.api

import com.financial.infrastructure.category.models.CreateRequest
import com.financial.infrastructure.category.models.CreateResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

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

}
package com.financial.infrastructure.api

import com.financial.infrastructure.account.models.CreateRequest
import com.financial.infrastructure.account.models.CreateResponse
import com.financial.infrastructure.account.models.FindByIdResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping(value = ["account"])
@Tag(name = "Account")
interface AccountAPI {

    @PutMapping(
        value = ["/"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    @Operation(summary = "Create Account")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Created Account"),
            ApiResponse(responseCode = "422", description = "A validation error was observed"),
            ApiResponse(responseCode = "500", description = "An unpredictable error was observed"),
        ]
    )
    fun create(@RequestBody account: CreateRequest): ResponseEntity<CreateResponse>

    @GetMapping(
        value = ["/{id}"], produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    @Operation(summary = "Find Account by id")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Retrieved Account"),
            ApiResponse(responseCode = "404", description = "Not Found Account id"),
            ApiResponse(responseCode = "500", description = "An unpredictable error was observed"),
        ]
    )
    fun findById(@PathVariable(name = "id") id: String): ResponseEntity<FindByIdResponse>
}
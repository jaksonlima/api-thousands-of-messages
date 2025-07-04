package com.financial.infrastructure.api.controllers

import com.financial.application.account.AccountCreateUseCase
import com.financial.infrastructure.account.models.CreateRequest
import com.financial.infrastructure.account.models.CreateResponse
import com.financial.infrastructure.account.models.FindByIdResponse
import com.financial.infrastructure.api.AccountAPI
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
class AccountController(
    val accountCreateUseCase: AccountCreateUseCase,
) : AccountAPI {

    override fun create(account: CreateRequest): ResponseEntity<CreateResponse> {
        val result = this.accountCreateUseCase.execute(account)

        return ResponseEntity.created(URI.create("/account/${result.id()}"))
            .body(CreateResponse.from(result))
    }

    override fun findById(id: String): ResponseEntity<FindByIdResponse> {
        TODO("Not yet implemented")
    }

}


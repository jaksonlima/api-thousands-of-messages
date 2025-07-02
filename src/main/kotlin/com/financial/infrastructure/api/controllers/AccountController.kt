package com.financial.infrastructure.api.controllers

import com.financial.application.account.AccountCreateUseCase
import com.financial.infrastructure.account.models.CreateRequest
import com.financial.infrastructure.account.models.CreateResponse
import com.financial.infrastructure.api.AccountAPI
import com.financial.infrastructure.kafka.producers.AccountCreateProducer
import org.springframework.http.ResponseEntity
import java.net.URI

//@RestController
class AccountController(
    val accountCreateUseCase: AccountCreateUseCase,
    val accountCreateProducer: AccountCreateProducer
) : AccountAPI {

    override fun create(account: CreateRequest): ResponseEntity<CreateResponse> {
        val result = this.accountCreateUseCase.execute(account)

        return ResponseEntity.created(URI.create("/account/${result.id()}"))
            .body(CreateResponse.from(result))
    }

    override fun findById(id: String): ResponseEntity<com.financial.infrastructure.account.models.FindByIdResponse> {
        TODO("Not yet implemented")
    }

}


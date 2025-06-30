package com.financial.infrastructure.api.controllers

import com.financial.application.wallet.create.CreateAccountUseCase
import com.financial.application.wallet.retrive.get.GetAccountUseCase
import com.financial.infrastructure.api.AccountAPI
import com.financial.infrastructure.wallet.models.CreateRequest
import com.financial.infrastructure.wallet.models.CreateResponse
import com.financial.infrastructure.wallet.models.FindByIdResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
class AccountController(
    val createAccountUseCase: CreateAccountUseCase,
    val getAccountUseCase: GetAccountUseCase
) : AccountAPI {

    override fun create(wallet: CreateRequest): ResponseEntity<CreateResponse> {
        val result = this.createAccountUseCase.execute(wallet)

        return ResponseEntity.created(URI.create("/wallet/${result.id()}"))
            .body(CreateResponse.from(result))
    }

    override fun findById(id: String): ResponseEntity<FindByIdResponse> {
        val result = this.getAccountUseCase.execute(id)

        return ResponseEntity.ofNullable(FindByIdResponse.from(result));
    }
}
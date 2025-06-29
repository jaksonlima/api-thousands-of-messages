package com.financial.infrastructure.api.controllers

import com.financial.application.wallet.create.CreateWalletUseCase
import com.financial.application.wallet.retrive.get.GetWalletUseCase
import com.financial.infrastructure.api.WalletAPI
import com.financial.infrastructure.wallet.models.CreateRequest
import com.financial.infrastructure.wallet.models.CreateResponse
import com.financial.infrastructure.wallet.models.FindByIdResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
class WallerController(
    val createWalletUseCase: CreateWalletUseCase,
    val getWalletUseCase: GetWalletUseCase
) : WalletAPI {

    override fun create(wallet: CreateRequest): ResponseEntity<CreateResponse> {
        val result = this.createWalletUseCase.execute(wallet)

        return ResponseEntity.created(URI.create("/wallet/${result.id()}"))
            .body(CreateResponse.from(result))
    }

    override fun findById(id: String): ResponseEntity<FindByIdResponse> {
        val result = this.getWalletUseCase.execute(id)

        return ResponseEntity.ofNullable(FindByIdResponse.from(result));
    }
}
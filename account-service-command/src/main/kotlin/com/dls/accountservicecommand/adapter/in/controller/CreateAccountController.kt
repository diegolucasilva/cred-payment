package com.dls.accountservicecommand.adapter.`in`.controller

import com.dls.accountservicecommand.adapter.`in`.command.CreateAccountCommand
import com.dls.accountservicecommand.adapter.`in`.controller.dto.CreateAccountRequest
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*
import java.util.concurrent.CompletableFuture
import javax.validation.Valid


@RestController
@RequestMapping("/account")
class CreateAccountController(private val commandGateway: CommandGateway) {

    @PostMapping
    fun createProduct(@Valid @RequestBody request: CreateAccountRequest): CompletableFuture<UUID>{
        val createAccountCommand = CreateAccountCommand(
            accountId =UUID.randomUUID(),
            customerId = request.customerId,
            balance = request.balance
        )
        return commandGateway.send(createAccountCommand)
    }


}
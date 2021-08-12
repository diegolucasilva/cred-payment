package com.dls.orderservice.adapter.`in`.controller

import com.dls.orderservice.adapter.`in`.command.CreateOrderCommand
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.queryhandling.QueryGateway
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("/order")
class OrdersCommandController(
    private val commandGateway: CommandGateway,
    private val queryGateway: QueryGateway
) {

    @PostMapping
    fun createProduct(@Valid @RequestBody createOrderRequest: CreateOrderRequest){

        val createOrderCommand = CreateOrderCommand(
            orderId = UUID.randomUUID(),
            fromAccountId = createOrderRequest.fromAccountId,
            toAccountId = createOrderRequest.toAccountId,
            amount = createOrderRequest.amount
        )
        commandGateway.sendAndWait<Any>(createOrderCommand)


    }
}
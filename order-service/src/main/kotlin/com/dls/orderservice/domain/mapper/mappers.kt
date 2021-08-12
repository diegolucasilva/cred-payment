package com.dls.orderservice.domain.mapper

import com.dls.orderservice.adapter.`in`.command.CreateOrderCommand
import com.dls.orderservice.domain.event.OrderCreatedEvent


fun CreateOrderCommand.toOrderCreatedEvent() =
    OrderCreatedEvent(orderId, fromAccountId, toAccountId, amount)

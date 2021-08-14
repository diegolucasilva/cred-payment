package com.dls.orderservice.domain.mapper

import com.dls.orderservice.adapter.`in`.command.ApproveOrderCommand
import com.dls.orderservice.adapter.`in`.command.CreateOrderCommand
import com.dls.orderservice.domain.event.OrderApprovedEvent
import com.dls.orderservice.domain.event.OrderCreatedEvent
import com.dls.orderservice.domain.event.OrderStatus


fun CreateOrderCommand.toOrderCreatedEvent() =
    OrderCreatedEvent(orderId, fromAccountId, toAccountId, amount, OrderStatus.CREATED)

fun ApproveOrderCommand.toOrderApprovedEvent() =
    OrderApprovedEvent(orderId, fromAccountId, toAccountId, amount, OrderStatus.COMPLETED)

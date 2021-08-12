package dls.example.orderservicequery.domain.mapper

import com.dls.orderservice.domain.event.OrderCreatedEvent
import dls.example.orderservicequery.adapter.`in`.controller.OrderResponse
import dls.example.orderservicequery.adapter.out.persitency.OrderEntity

fun OrderCreatedEvent.toOrderEntity() =
    OrderEntity(orderId, fromAccountId, toAccountId, amount, orderStatus.name)

fun OrderEntity.toOrderResponse() =
    OrderResponse(orderId, fromAccountId, toAccountId, amount, orderStatus)

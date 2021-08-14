package com.dls.orderservice.domain.event

import java.util.*

data class OrderApprovedEvent(
    val orderId: UUID,
    val fromAccountId: UUID,
    val toAccountId: UUID,
    val amount: Double,
    val orderStatus: OrderStatus
)
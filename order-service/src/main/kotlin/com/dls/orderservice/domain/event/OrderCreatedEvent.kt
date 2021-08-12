package com.dls.orderservice.domain.event

import java.util.*

data class OrderCreatedEvent(
    val orderId: UUID,
    val fromAccountId: UUID,
    val toAccountId: UUID,
    val amount: Double
)
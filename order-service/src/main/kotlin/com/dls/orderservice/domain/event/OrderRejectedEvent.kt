package com.dls.orderservice.domain.event

import java.util.*

data class OrderRejectedEvent(
    val orderId: UUID,
    val orderStatus: OrderStatus,
    val reason: String
)
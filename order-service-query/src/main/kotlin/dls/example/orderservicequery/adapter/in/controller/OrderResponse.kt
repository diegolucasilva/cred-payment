package dls.example.orderservicequery.adapter.`in`.controller

import java.util.*

data class OrderResponse(
    val orderId: UUID,
    val fromAccountId: UUID,
    val toAccountId: UUID,
    val amount: Double,
    val orderStatus: String)
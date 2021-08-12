package com.dls.orderservice.adapter.`in`.controller

import java.util.*
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

data class CreateOrderRequest(
    @field:NotNull(message = "From Account id can't be null")
    val fromAccountId: UUID,
    @field:NotNull(message = "From Account id can't be null")
    val toAccountId: UUID,
    @field:Min(value = 1, message = "Quantity can't be lower than 1")
    val amount: Double
)
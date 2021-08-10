package com.dls.accountservicecommand.adapter.`in`.controller.dto

import java.util.*
import javax.validation.constraints.Min

data class CreateAccountResponse(
    val accountId: UUID,
    val customerId: UUID,
    @field:Min(value=0, message="must be positive")
    val balance: Double,
)
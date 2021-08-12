package com.dls.accountservicequery.adapter.`in`.controller

import java.util.*

data class AccountResponse(
    val accountId: UUID,
    val customerId: String,
    val balance: Double
)
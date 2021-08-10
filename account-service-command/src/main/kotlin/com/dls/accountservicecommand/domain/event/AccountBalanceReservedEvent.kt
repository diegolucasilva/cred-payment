package com.dls.accountservicecommand.domain.event

import com.dls.accountservicecommand.domain.aggregate.Account
import java.util.*

data class AccountBalanceReservedEvent(
    val accountId: UUID,
    val customerId: String,
    val amount: Double)
package com.dls.accountservicecommand.domain.event

import java.util.*

data class AccountBalanceReservedEvent(
    val accountId: UUID,
    val toAccountId: UUID,
    val orderId: UUID,
    val amount: Double)
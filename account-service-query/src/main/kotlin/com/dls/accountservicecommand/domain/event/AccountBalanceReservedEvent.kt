package com.dls.accountservicecommand.domain.event

import java.util.*

data class AccountBalanceReservedEvent(
    val accountId: UUID,
    val amount: Double)
package com.dls.accountservicecommand.domain.event

import java.util.*

class AccountDebitCancelledEvent(
    val accountId: UUID,
    val amount: Double,
    val reason: String)

package com.dls.accountservicecommand.domain.event

import java.util.*

class AccountCreditCancelledEvent(
    val accountId: UUID,
    val amount: Double,
    val reason: String)

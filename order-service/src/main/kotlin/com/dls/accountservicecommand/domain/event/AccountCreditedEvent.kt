package com.dls.accountservicecommand.domain.event

import java.util.*

class AccountCreditedEvent(
    val accountId: UUID,
    val fromAccountId: UUID,
    val orderId: UUID,
    val amount: Double)

package com.dls.accountservicecommand.domain.event

import java.util.*

class AccountDebitedEvent(
    val accountId: UUID,
    val orderId: UUID,
    val amount: Double)

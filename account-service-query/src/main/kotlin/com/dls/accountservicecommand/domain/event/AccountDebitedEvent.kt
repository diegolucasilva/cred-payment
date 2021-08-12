package com.dls.accountservicecommand.domain.event

import java.util.*

class AccountDebitedEvent(
    val accountId: UUID,
    val amount: Double)

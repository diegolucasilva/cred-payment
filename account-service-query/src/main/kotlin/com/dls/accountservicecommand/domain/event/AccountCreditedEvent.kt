package com.dls.accountservicecommand.domain.event

import java.util.*

class AccountCreditedEvent(
    val accountId: UUID,
    val amount: Double)

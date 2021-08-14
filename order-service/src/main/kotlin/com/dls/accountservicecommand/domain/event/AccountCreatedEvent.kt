package com.dls.accountservicecommand.domain.event

import java.util.*

class AccountCreatedEvent(
    val accountId: UUID,
    val customerId: String,
    val balance: Double)

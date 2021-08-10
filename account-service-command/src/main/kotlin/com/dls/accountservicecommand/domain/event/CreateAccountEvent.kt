package com.dls.accountservicecommand.domain.event

import java.util.*

class CreateAccountEvent(
    val accountId: UUID,
    val customerId: String,
    val balance: Double)

package com.dls.accountservicecommand.domain.event

import com.dls.accountservicecommand.domain.aggregate.Account
import java.util.*

class CreateAccountEvent(
    val accountId: UUID,
    val customerId: String,
    val balance: Double,
    val accountStatus: Account.AccountBalanceStatus
)

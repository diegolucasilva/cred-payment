package com.dls.accountservicecommand.domain.mapper

import com.dls.accountservicecommand.adapter.`in`.command.CreateAccountCommand
import com.dls.accountservicecommand.domain.aggregate.Account
import com.dls.accountservicecommand.domain.event.CreateAccountEvent

fun CreateAccountCommand.toCreateAccountEvent() =
    CreateAccountEvent(
        accountId =accountId,
        customerId = customerId,
        balance = balance,
        accountStatus = Account.AccountBalanceStatus.FREE)




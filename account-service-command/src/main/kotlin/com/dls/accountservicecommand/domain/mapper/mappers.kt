package com.dls.accountservicecommand.domain.mapper

import com.dls.accountservicecommand.adapter.`in`.command.CreateAccountCommand
import com.dls.accountservicecommand.adapter.`in`.command.ReserveBalanceAccountCommand
import com.dls.accountservicecommand.domain.aggregate.Account
import com.dls.accountservicecommand.domain.event.CreateAccountEvent
import com.dls.accountservicecommand.domain.event.ReserveBalanceAccountEvent

fun CreateAccountCommand.toCreateAccountEvent() =
    CreateAccountEvent(
        accountId =accountId,
        customerId = customerId,
        balance = balance)


fun ReserveBalanceAccountCommand.toReserveBalanceAccountEvent() =
    ReserveBalanceAccountEvent(
        accountId =accountId,
        customerId = customerId,
        amount = amount)




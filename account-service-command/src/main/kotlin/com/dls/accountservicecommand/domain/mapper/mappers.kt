package com.dls.accountservicecommand.domain.mapper

import com.dls.accountservicecommand.adapter.`in`.command.CreateAccountCommand
import com.dls.accountservicecommand.adapter.`in`.command.CreditAccountCommand
import com.dls.accountservicecommand.adapter.`in`.command.ReserveBalanceAccountCommand
import com.dls.accountservicecommand.domain.event.AccountCreatedEvent
import com.dls.accountservicecommand.domain.event.AccountBalanceReservedEvent
import com.dls.accountservicecommand.domain.event.AccountCreditedEvent

fun CreateAccountCommand.toAccountCreatedEvent() =
    AccountCreatedEvent(
        accountId =accountId,
        customerId = customerId,
        balance = balance)

fun ReserveBalanceAccountCommand.toAccountBalanceReservedEvent() =
    AccountBalanceReservedEvent(
        accountId =accountId,
        amount = amount)

fun CreditAccountCommand.toAccountCreditedEvent() =
    AccountCreditedEvent(
        accountId =accountId,
        amount = amount)



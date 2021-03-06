package com.dls.accountservicecommand.domain.mapper

import com.dls.accountservicecommand.adapter.`in`.command.*
import com.dls.accountservicecommand.domain.event.*

fun CreateAccountCommand.toAccountCreatedEvent() =
    AccountCreatedEvent(
        accountId =accountId,
        customerId = customerId,
        balance = balance)

fun ReserveBalanceAccountCommand.toAccountBalanceReservedEvent() =
    AccountBalanceReservedEvent(
        accountId =accountId,
        orderId =orderId,
        toAccountId = toAccountId,
        amount = amount)

fun CreditAccountCommand.toAccountCreditedEvent() =
    AccountCreditedEvent(
        accountId =accountId,
        orderId =orderId,
        fromAccountId = fromAccountId,
        amount = amount)

fun DebitAccountCommand.toAccountDebitedEvent() =
    AccountDebitedEvent(
        accountId =accountId,
        orderId =orderId,
        fromAccountId = fromAccountId,
        amount = amount)


fun CancelCreditAccountCommand.toAccountCreditCancelledEvent() =
    AccountCreditCancelledEvent(
        accountId =accountId,
        amount = amount,
        reason = reason)

fun CancelDebitAccountCommand.toAccountDebitCancelledEvent() =
    AccountDebitCancelledEvent(
        accountId =accountId,
        amount = amount,
        reason = reason)

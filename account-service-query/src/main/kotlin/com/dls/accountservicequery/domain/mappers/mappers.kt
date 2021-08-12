package com.dls.accountservicequery.domain.mappers

import com.dls.accountservicecommand.domain.event.AccountCreatedEvent
import com.dls.accountservicequery.adapter.`in`.controller.AccountResponse
import com.dls.accountservicequery.adapter.out.AccountEntity

fun AccountCreatedEvent.toAccountEntity() =
    AccountEntity(
        accountId =accountId,
        customerId = customerId,
        balance = balance)

fun AccountEntity.toAccountResponse() =
    AccountResponse(
        accountId =accountId,
        customerId = customerId,
        balance = balance)
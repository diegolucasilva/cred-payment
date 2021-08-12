package com.dls.accountservicequery.domain

import com.dls.accountservicecommand.domain.event.AccountCreatedEvent
import com.dls.accountservicequery.adapter.`in`.controller.AccountResponse
import com.dls.accountservicequery.adapter.`in`.controller.FindAccountByIdQuery
import com.dls.accountservicequery.domain.mappers.toAccountEntity
import com.dls.accountservicequery.domain.mappers.toAccountResponse
import com.dls.accountservicequery.domain.port.out.persistency.AccountRepository
import org.axonframework.config.ProcessingGroup
import org.axonframework.eventhandling.EventHandler
import org.axonframework.queryhandling.QueryHandler
import org.springframework.stereotype.Component

@Component
@ProcessingGroup("account-group")
class AccountEventHandler(private val accountRepository: AccountRepository) {

    @EventHandler
    fun on(accountCreatedEvent: AccountCreatedEvent){
        logger.info("EventHandler AccountEventHandler ${accountCreatedEvent.accountId}")
        val accountEntity = accountCreatedEvent.toAccountEntity()
        accountRepository.save(accountEntity)
    }

    @QueryHandler
    fun on(findAccountByIdQuery: FindAccountByIdQuery): AccountResponse {
        logger.info("QueryHandler FindAccountByIdQuery")
        val accountEntity = accountRepository.findById(findAccountByIdQuery.accountId).get()
        return accountEntity.toAccountResponse()
    }

    companion object {
        private val logger = org.slf4j.LoggerFactory.getLogger(AccountCreatedEvent::class.java)
    }
}
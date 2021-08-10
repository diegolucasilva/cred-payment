package com.dls.accountservicecommand.domain.aggregate

import com.dls.accountservicecommand.adapter.`in`.command.CreateAccountCommand
import com.dls.accountservicecommand.domain.event.CreateAccountEvent
import com.dls.accountservicecommand.domain.mapper.toCreateAccountEvent
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.spring.stereotype.Aggregate
import org.slf4j.LoggerFactory
import java.util.*
import kotlin.properties.Delegates

@Aggregate
class Account(){
    @AggregateIdentifier
    private lateinit var accountId: UUID
    private lateinit var customerId: String
    private var balance by Delegates.notNull<Double>()
    private lateinit var accountStatus: AccountBalanceStatus
    enum class AccountBalanceStatus{FREE, RESERVED}

    @CommandHandler
    constructor(createAccountCommand: CreateAccountCommand) : this() {
        logger.info("CommandHandler CreateAccountCommand. Account id ${createAccountCommand.accountId}")
        val createAccountEvent = createAccountCommand.toCreateAccountEvent()
        AggregateLifecycle.apply(createAccountEvent);
    }

    companion object {
        private val logger = LoggerFactory.getLogger(Account::class.java)
    }

    @EventSourcingHandler
    fun on(createAccountEvent: CreateAccountEvent) {
        logger.info("EventSourcingHandler CreateAccountEvent to account ${createAccountEvent.accountId}")
        accountId = createAccountEvent.accountId
        customerId = createAccountEvent.customerId
        balance = createAccountEvent.balance
        accountStatus = createAccountEvent.accountStatus
    }
}
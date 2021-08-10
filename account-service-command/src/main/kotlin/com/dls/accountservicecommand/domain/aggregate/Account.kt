package com.dls.accountservicecommand.domain.aggregate

import com.dls.accountservicecommand.adapter.`in`.command.CreateAccountCommand
import com.dls.accountservicecommand.adapter.`in`.command.ReserveBalanceAccountCommand
import com.dls.accountservicecommand.domain.event.AccountCreatedEvent
import com.dls.accountservicecommand.domain.event.AccountBalanceReservedEvent
import com.dls.accountservicecommand.domain.exception.InsufficientBalanceException
import com.dls.accountservicecommand.domain.mapper.toAccountCreatedEvent
import com.dls.accountservicecommand.domain.mapper.toAccountBalanceReservedEvent
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
    constructor(command: CreateAccountCommand) : this() {
        logger.info("CommandHandler CreateAccountCommand. Account id ${command.accountId}")
        val event = command.toAccountCreatedEvent()
        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    fun handle(command: ReserveBalanceAccountCommand){
        logger.info("CommandHandler reserveBalanceAccountCommand. Account id ${command.accountId}")
        val event = command.toAccountBalanceReservedEvent()
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    fun on(accountBalanceReservedEvent: AccountBalanceReservedEvent) {
        logger.info("EventSourcingHandler AccountBalanceReservedEvent to account ${accountBalanceReservedEvent.accountId}")
        accountId = accountBalanceReservedEvent.accountId
        customerId = accountBalanceReservedEvent.customerId
        reserveBalanceAccount(accountBalanceReservedEvent.amount)
    }

    @EventSourcingHandler
    fun on(accountCreatedEvent: AccountCreatedEvent) {
        logger.info("EventSourcingHandler AccountCreatedEvent to account ${accountCreatedEvent.accountId}")
        accountId = accountCreatedEvent.accountId
        customerId = accountCreatedEvent.customerId
        balance = accountCreatedEvent.balance
        accountStatus = AccountBalanceStatus.FREE
    }

    private fun reserveBalanceAccount(amount: Double){
        if(balance < amount)
            throw InsufficientBalanceException(balance, amount)
        accountStatus = AccountBalanceStatus.RESERVED
    }

    companion object {
        private val logger = LoggerFactory.getLogger(Account::class.java)
    }

}
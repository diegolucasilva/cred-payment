package com.dls.accountservicecommand.domain.aggregate

import com.dls.accountservicecommand.adapter.`in`.command.*
import com.dls.accountservicecommand.domain.event.*
import com.dls.accountservicecommand.domain.exception.AccountAlreadyReservedException
import com.dls.accountservicecommand.domain.exception.InsufficientBalanceException
import com.dls.accountservicecommand.domain.mapper.*

import org.axonframework.commandhandling.CommandHandler
import org.axonframework.config.ProcessingGroup
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
    private lateinit var toAccountId: UUID
    private lateinit var fromAccountId: UUID
    private lateinit var orderId: UUID
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

    @CommandHandler
    fun handle(command: CreditAccountCommand){
        logger.info("CommandHandler CreditAccountCommand. Account id ${command.accountId}")
        val event = command.toAccountCreditedEvent()
        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    fun handle(command: DebitAccountCommand){
        logger.info("CommandHandler DebitAccountCommand. Account id ${command.accountId}")
        val event = command.toAccountDebitedEvent()
        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    fun handle(command: CancelCreditAccountCommand){
        logger.info("CommandHandler CancelCreditAccountCommand. Account id ${command.accountId}")
        val event = command.toAccountCreditCancelledEvent()
        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    fun handle(command: CancelDebitAccountCommand){
        logger.info("CommandHandler CancelDebitAccountCommand. Account id ${command.accountId}")
        val event = command.toAccountDebitCancelledEvent()
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    fun on(accountCreatedEvent: AccountCreatedEvent) {
        logger.info("EventSourcingHandler AccountCreatedEvent to account ${accountCreatedEvent.accountId}")
        accountId = accountCreatedEvent.accountId
        customerId = accountCreatedEvent.customerId
        balance = accountCreatedEvent.balance
        accountStatus = AccountBalanceStatus.FREE
    }

    @EventSourcingHandler
    fun on(accountBalanceReservedEvent: AccountBalanceReservedEvent) {
        logger.info("EventSourcingHandler AccountBalanceReservedEvent to account ${accountBalanceReservedEvent.accountId}")
        if(balance < accountBalanceReservedEvent.amount){
            throw InsufficientBalanceException(balance, accountBalanceReservedEvent.amount)
        }
         if(accountStatus == AccountBalanceStatus.RESERVED){
            throw AccountAlreadyReservedException(accountBalanceReservedEvent.accountId)
        }
        orderId = accountBalanceReservedEvent.orderId
        accountStatus = AccountBalanceStatus.RESERVED
        toAccountId = accountBalanceReservedEvent.toAccountId
    }

    @EventSourcingHandler
    fun on(accountCreditedEvent: AccountCreditedEvent){
        logger.info("EventSourcingHandler AccountCreditedEvent to account ${accountCreditedEvent.accountId}")
        balance += accountCreditedEvent.amount
        fromAccountId = accountCreditedEvent.fromAccountId
    }

    @EventSourcingHandler
    fun on(accountCreditCancelledEvent: AccountCreditCancelledEvent){
        logger.info("EventSourcingHandler AccountCreditedEvent to account ${accountCreditCancelledEvent.accountId}")
        balance -= accountCreditCancelledEvent.amount
    }

    @EventSourcingHandler
    fun on(accountDebitCancelledEvent: AccountDebitCancelledEvent){
        logger.info("EventSourcingHandler AccountDebitedEvent to account ${accountDebitCancelledEvent.accountId}")
        balance += accountDebitCancelledEvent.amount
    }

    @EventSourcingHandler
    fun on(accountDebitedEvent: AccountDebitedEvent){
        logger.info("EventSourcingHandler AccountDebitedEvent to account ${accountDebitedEvent.accountId}")
        balance -= accountDebitedEvent.amount
        accountStatus = AccountBalanceStatus.FREE
        fromAccountId = accountDebitedEvent.fromAccountId
    }

    companion object {
        private val logger = LoggerFactory.getLogger(Account::class.java)
    }

}




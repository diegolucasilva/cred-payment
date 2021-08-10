package com.dls.accountservicecommand.domain.aggregate

import com.dls.accountservicecommand.adapter.`in`.command.CreateAccountCommand
import com.dls.accountservicecommand.adapter.`in`.command.ReserveBalanceAccountCommand
import com.dls.accountservicecommand.domain.event.CreateAccountEvent
import com.dls.accountservicecommand.domain.event.ReserveBalanceAccountEvent
import com.dls.accountservicecommand.domain.exception.InsufficientBalanceException
import com.dls.accountservicecommand.domain.mapper.toCreateAccountEvent
import com.dls.accountservicecommand.domain.mapper.toReserveBalanceAccountEvent
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
        val event = command.toCreateAccountEvent()
        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    fun handle(command: ReserveBalanceAccountCommand){
        logger.info("CommandHandler reserveBalanceAccountCommand. Account id ${command.accountId}")
        val event = command.toReserveBalanceAccountEvent()
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    fun on(reserveBalanceAccountEvent: ReserveBalanceAccountEvent) {
        logger.info("EventSourcingHandler CreateAccountEvent to account ${reserveBalanceAccountEvent.accountId}")
        accountId = reserveBalanceAccountEvent.accountId
        customerId = reserveBalanceAccountEvent.customerId
        reserveBalanceAccount(reserveBalanceAccountEvent.amount)
    }

    @EventSourcingHandler
    fun on(createAccountEvent: CreateAccountEvent) {
        logger.info("EventSourcingHandler CreateAccountEvent to account ${createAccountEvent.accountId}")
        accountId = createAccountEvent.accountId
        customerId = createAccountEvent.customerId
        balance = createAccountEvent.balance
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
package com.dls.orderservice.domain.agregate

import com.dls.orderservice.adapter.`in`.command.CreateOrderCommand
import com.dls.orderservice.domain.event.OrderCreatedEvent
import com.dls.orderservice.domain.mapper.toOrderCreatedEvent
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.spring.stereotype.Aggregate
import org.slf4j.LoggerFactory
import java.util.*
import kotlin.properties.Delegates

@Aggregate
class Order() {
    @AggregateIdentifier
    private lateinit var orderId: UUID
    private lateinit var fromAccountId: UUID
    private lateinit var toAccountId: UUID
    private var amount by Delegates.notNull<Double>()
    private lateinit var orderStatus: OrderStatus
    enum class OrderStatus{CREATED, PROCESSING, COMPLETED}

    @CommandHandler
    constructor(createOrderCommand: CreateOrderCommand) : this() {
        logger.info("CommandHandler CreateOrderCommand to order ${createOrderCommand.orderId}")
        val orderCreatedEvent = createOrderCommand.toOrderCreatedEvent()
        AggregateLifecycle.apply(orderCreatedEvent);
    }

    @EventSourcingHandler
    fun on(orderCreatedEvent: OrderCreatedEvent) {
        logger.info("EventSourcingHandler OrderCreatedEvent to order ${orderCreatedEvent.orderId}")
        orderId = orderCreatedEvent.orderId
        fromAccountId = orderCreatedEvent.fromAccountId
        toAccountId = orderCreatedEvent.toAccountId
        amount = orderCreatedEvent.amount
        orderStatus = OrderStatus.CREATED
    }

    companion object {
        private val logger = LoggerFactory.getLogger(Order::class.java)
    }
}
package com.dls.orderservice.domain.agregate

import com.dls.orderservice.adapter.`in`.command.ApproveOrderCommand
import com.dls.orderservice.adapter.`in`.command.CreateOrderCommand
import com.dls.orderservice.adapter.`in`.command.RejectOrderCommand
import com.dls.orderservice.domain.event.OrderApprovedEvent
import com.dls.orderservice.domain.event.OrderCreatedEvent
import com.dls.orderservice.domain.event.OrderRejectedEvent
import com.dls.orderservice.domain.mapper.toOrderApprovedEvent
import com.dls.orderservice.domain.mapper.toOrderCreatedEvent
import com.dls.orderservice.domain.mapper.toOrderRejectedEvent
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
    private lateinit var reason: String
    private var amount by Delegates.notNull<Double>()
    private lateinit var orderStatus: OrderStatus
    enum class OrderStatus{CREATED, PROCESSING, COMPLETED, REJECTED}

    @CommandHandler
    constructor(createOrderCommand: CreateOrderCommand) : this() {
        logger.info("CommandHandler CreateOrderCommand to order ${createOrderCommand.orderId}")
        val orderCreatedEvent = createOrderCommand.toOrderCreatedEvent()
        AggregateLifecycle.apply(orderCreatedEvent);
    }

    @CommandHandler
    fun handle(approveOrderCommand: ApproveOrderCommand){
        logger.info("CommandHandler ApproveOrderCommand to order ${approveOrderCommand.orderId}")
        val orderApprovedEvent = approveOrderCommand.toOrderApprovedEvent()
        AggregateLifecycle.apply(orderApprovedEvent);
    }

    @CommandHandler
    fun handle(orderRejectOrderCommand: RejectOrderCommand){
        logger.info("CommandHandler RejectOrderCommand to order ${orderRejectOrderCommand.orderId}")
        val orderRejectedEvent = orderRejectOrderCommand.toOrderRejectedEvent()
        AggregateLifecycle.apply(orderRejectedEvent);
    }

    @EventSourcingHandler
    fun on(orderCreatedEvent: OrderCreatedEvent) {
        logger.info("EventSourcingHandler OrderCreatedEvent to order ${orderCreatedEvent.orderId}")
        orderId = orderCreatedEvent.orderId
        fromAccountId = orderCreatedEvent.fromAccountId
        toAccountId = orderCreatedEvent.toAccountId
        amount = orderCreatedEvent.amount
        orderStatus = OrderStatus.valueOf(orderCreatedEvent.orderStatus.name)
    }

    @EventSourcingHandler
    fun on(orderApprovedEvent: OrderApprovedEvent) {
        logger.info("EventSourcingHandler OrderApprovedEvent to order ${orderApprovedEvent.orderId}")
        orderStatus = OrderStatus.valueOf(orderApprovedEvent.orderStatus.name)
    }

    @EventSourcingHandler
    fun on(orderRejectedEvent: OrderRejectedEvent) {
        logger.info("EventSourcingHandler OrderRejectedEvent to order ${orderRejectedEvent.orderId}")
        orderStatus = OrderStatus.valueOf(orderRejectedEvent.orderStatus.name)
        reason = orderRejectedEvent.reason
    }

    companion object {
        private val logger = LoggerFactory.getLogger(Order::class.java)
    }
}
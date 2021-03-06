package com.dls.orderservice.adapter.`in`.saga

import com.dls.accountservicecommand.adapter.`in`.command.CreditAccountCommand
import com.dls.accountservicecommand.adapter.`in`.command.DebitAccountCommand
import com.dls.accountservicecommand.adapter.`in`.command.ReserveBalanceAccountCommand
import com.dls.accountservicecommand.domain.event.AccountBalanceReservedEvent
import com.dls.accountservicecommand.domain.event.AccountCreditedEvent
import com.dls.accountservicecommand.domain.event.AccountDebitedEvent
import com.dls.orderservice.adapter.`in`.command.ApproveOrderCommand
import com.dls.orderservice.adapter.`in`.command.RejectOrderCommand
import com.dls.orderservice.domain.event.OrderCreatedEvent
import com.dls.orderservice.domain.event.OrderRejectedEvent
import org.axonframework.commandhandling.CommandMessage
import org.axonframework.commandhandling.CommandResultMessage
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.interceptors.ExceptionHandler
import org.axonframework.modelling.saga.EndSaga
import org.axonframework.modelling.saga.SagaEventHandler
import org.axonframework.modelling.saga.StartSaga
import org.axonframework.queryhandling.QueryGateway
import org.axonframework.queryhandling.QueryUpdateEmitter
import org.axonframework.spring.stereotype.Saga
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import java.util.NoSuchElementException

@Saga
class OrderSaga(){
    @Autowired
    private var commandGateway: CommandGateway? = null

    @Autowired
    private val queryGateway: QueryGateway? = null

    @Autowired
    @Transient
    private val queryUpdateEmitter: QueryUpdateEmitter? = null

    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    fun handle(orderCreatedEvent: OrderCreatedEvent) {
        logger.info("Saga OrderCreatedEvent $orderCreatedEvent")

        val reserveBalanceAccountCommand = ReserveBalanceAccountCommand(
            accountId = orderCreatedEvent.fromAccountId,
            toAccountId = orderCreatedEvent.toAccountId,
            orderId = orderCreatedEvent.orderId,
            amount = orderCreatedEvent.amount
        )
        commandGateway?.send<ReserveBalanceAccountCommand, Any>(reserveBalanceAccountCommand,
            { _: CommandMessage<out ReserveBalanceAccountCommand>, commandResultMessage: CommandResultMessage<*> ->
                if(commandResultMessage.isExceptional){
                    logger.error("Saga OrderCreatedEvent failed $orderCreatedEvent. Rejecting order")
                    val rejectOrderCommand = RejectOrderCommand(
                        orderId = orderCreatedEvent.orderId,
                        reason = commandResultMessage.exceptionResult().message!!
                    )
                    commandGateway?.send<Any>(rejectOrderCommand)
                    throw commandResultMessage.optionalExceptionResult().get()
                }

                logger.info("Result $commandResultMessage")
            })
    }

    @SagaEventHandler(associationProperty = "orderId")
    fun handle(accountBalanceReservedEvent: AccountBalanceReservedEvent) {
        logger.info("Saga AccountBalanceReservedEvent $accountBalanceReservedEvent")

        val creditAccountCommand = CreditAccountCommand(
            accountId = accountBalanceReservedEvent.toAccountId,
            fromAccountId = accountBalanceReservedEvent.accountId,
            orderId = accountBalanceReservedEvent.orderId,
            amount = accountBalanceReservedEvent.amount,
        )
        commandGateway?.send<CreditAccountCommand>(creditAccountCommand)
    }

    @SagaEventHandler(associationProperty = "orderId")
    fun handle(accountCreditedEvent: AccountCreditedEvent) {
        logger.info("Saga AccountCreditedEvent $accountCreditedEvent")

        val debitAccountCommand = DebitAccountCommand(
            accountId = accountCreditedEvent.fromAccountId,
            fromAccountId = accountCreditedEvent.accountId,
            orderId = accountCreditedEvent.orderId,
            amount = accountCreditedEvent.amount,
        )
        commandGateway?.send<DebitAccountCommand>(debitAccountCommand)
    }

    @SagaEventHandler(associationProperty = "orderId")
    fun handle(accountDebitedEvent: AccountDebitedEvent) {
        logger.info("Saga AccountDebitedEvent $accountDebitedEvent")

        val approveOrderCommand = ApproveOrderCommand(
            fromAccountId = accountDebitedEvent.accountId,
            toAccountId = accountDebitedEvent.fromAccountId,
            orderId = accountDebitedEvent.orderId,
            amount = accountDebitedEvent.amount,
        )
        commandGateway?.send<ApproveOrderCommand>(approveOrderCommand)
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "orderId")
    fun handle(orderRejectedEvent: OrderRejectedEvent){
        logger.error("Saga OrderRejectedEvent. Order successfully rejected for user ${orderRejectedEvent.orderId}")
    }


    companion object {
        private val logger = LoggerFactory.getLogger(OrderSaga::class.java)
    }

}
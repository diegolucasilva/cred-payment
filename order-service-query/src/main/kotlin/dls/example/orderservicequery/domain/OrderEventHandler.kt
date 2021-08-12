package dls.example.orderservicequery.domain

import com.dls.orderservice.domain.event.OrderCreatedEvent
import dls.example.orderservicequery.adapter.`in`.controller.FindOrderByIdQuery
import dls.example.orderservicequery.adapter.`in`.controller.OrderResponse
import dls.example.orderservicequery.domain.mapper.toOrderEntity
import dls.example.orderservicequery.domain.mapper.toOrderResponse
import dls.example.orderservicequery.domain.port.out.persistency.OrderRepository
import org.axonframework.config.ProcessingGroup
import org.axonframework.eventhandling.EventHandler
import org.axonframework.queryhandling.QueryHandler
import org.springframework.stereotype.Component

@Component
@ProcessingGroup("order-group")
class OrderEventHandler(private val orderRepository: OrderRepository) {

    @EventHandler
    fun on(orderCreatedEvent: OrderCreatedEvent){
        logger.info("EventHandler OrderCreatedEvent ${orderCreatedEvent.orderId}")
        val accountEntity = orderCreatedEvent.toOrderEntity()
        orderRepository.save(accountEntity)
    }

    @QueryHandler
    fun on(findOrderByIdQuery: FindOrderByIdQuery): OrderResponse {
        logger.info("QueryHandler FindOrderByIdQuery")
        val orderEntity = orderRepository.findById(findOrderByIdQuery.orderId).get()
        return orderEntity.toOrderResponse()
    }

    companion object {
        private val logger = org.slf4j.LoggerFactory.getLogger(OrderEventHandler::class.java)
    }
}
package dls.example.orderservicequery.adapter.`in`.controller

import org.axonframework.messaging.responsetypes.ResponseTypes
import org.axonframework.queryhandling.QueryGateway
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*
import java.util.concurrent.CompletableFuture

@RestController
@RequestMapping("/order")
class FindOrderByIdController(private val queryGateway: QueryGateway) {

    @GetMapping("/{orderId}")
    fun getOrderById(@PathVariable orderId: UUID): CompletableFuture<OrderResponse> {
        val findOrderByIdQuery = FindOrderByIdQuery(orderId)
        return queryGateway.query(
            findOrderByIdQuery,
            ResponseTypes.instanceOf(OrderResponse::class.java)
        )
    }
}
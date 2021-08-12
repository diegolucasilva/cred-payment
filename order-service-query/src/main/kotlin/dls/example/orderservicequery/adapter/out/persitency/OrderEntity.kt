package dls.example.orderservicequery.adapter.out.persitency

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import java.util.*

@RedisHash("OrderEntity")
class OrderEntity(
    @Id
    val orderId: UUID,
    val fromAccountId: UUID,
    val toAccountId: UUID,
    val amount: Double,
    val orderStatus: String
)
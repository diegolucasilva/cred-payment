package dls.example.orderservicequery.domain.port.out.persistency

import dls.example.orderservicequery.adapter.out.persitency.OrderEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface OrderRepository: CrudRepository<OrderEntity, UUID>
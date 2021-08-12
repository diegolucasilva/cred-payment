package com.dls.accountservicequery.adapter.out

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import java.util.*

@RedisHash("AccountEntity")
class AccountEntity(
    @Id
    val accountId: UUID,
    val customerId: String,
    var balance: Double
)
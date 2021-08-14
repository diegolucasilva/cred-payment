package com.dls.accountservicecommand.adapter.`in`.command

import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.util.*

data class ReserveBalanceAccountCommand(
    @TargetAggregateIdentifier
    val accountId: UUID,
    val toAccountId: UUID,
    val orderId: UUID,
    val amount: Double,
    )
package com.dls.accountservicecommand.adapter.`in`.command

import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.util.*

data class DebitAccountCommand(
    @TargetAggregateIdentifier
    val accountId: UUID,
    val amount: Double)
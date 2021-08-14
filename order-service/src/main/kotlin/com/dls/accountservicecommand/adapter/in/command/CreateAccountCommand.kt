package com.dls.accountservicecommand.adapter.`in`.command

import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.util.*

data class CreateAccountCommand(
    @TargetAggregateIdentifier
    val accountId: UUID,
    val customerId: String,
    val balance: Double)
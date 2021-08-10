package com.dls.accountservicecommand.domain.exception

class InsufficientBalanceException(private val balance: Double, private val amount: Double ) :
    RuntimeException("The current balance $ $balance is insufficient. Transaction value $ $amount")
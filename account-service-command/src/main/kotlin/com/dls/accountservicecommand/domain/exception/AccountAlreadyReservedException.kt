package com.dls.accountservicecommand.domain.exception

import java.util.*

class AccountAlreadyReservedException(private val accountId: UUID) :
    RuntimeException("The account $accountId status is already reserved")
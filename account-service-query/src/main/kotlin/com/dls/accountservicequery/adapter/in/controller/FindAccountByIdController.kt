package com.dls.accountservicequery.adapter.`in`.controller

import org.axonframework.messaging.responsetypes.ResponseTypes
import org.axonframework.queryhandling.QueryGateway
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*
import java.util.concurrent.CompletableFuture


@RestController
@RequestMapping("/account")
class FindAccountByIdController(private val queryGateway: QueryGateway) {

    @GetMapping("/{accountId}")
    fun getAccountById(@PathVariable accountId: UUID): CompletableFuture<AccountResponse> {
        val findProductsQuery = FindAccountByIdQuery(accountId)
        return queryGateway.query(
            findProductsQuery,
            ResponseTypes.instanceOf(AccountResponse::class.java)
        )
    }
}
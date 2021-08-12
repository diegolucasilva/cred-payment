package com.dls.accountservicequery

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories

@SpringBootApplication
class AccountServiceQueryApplication

fun main(args: Array<String>) {
	runApplication<AccountServiceQueryApplication>(*args)
}

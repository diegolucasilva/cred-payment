package com.dls.accountservicecommand

import com.dls.accountservicecommand.config.AxonConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

@SpringBootApplication
//@Import (AxonConfig::class)
class AccountServiceCommandApplication

fun main(args: Array<String>) {
	runApplication<AccountServiceCommandApplication>(*args)
}

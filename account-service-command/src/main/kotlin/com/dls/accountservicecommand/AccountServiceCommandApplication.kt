package com.dls.accountservicecommand

import com.dls.accountservicecommand.config.AxonConfig
import org.axonframework.config.EventProcessingConfigurer
import org.axonframework.eventhandling.PropagatingErrorHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

@SpringBootApplication
//@Import (AxonConfig::class)
class AccountServiceCommandApplication{

	@Autowired
	fun eventProcessingConfigurer(config: EventProcessingConfigurer){
		config.registerListenerInvocationErrorHandler("account-group") { PropagatingErrorHandler.instance() }
	}
}

fun main(args: Array<String>) {
	runApplication<AccountServiceCommandApplication>(*args)
}

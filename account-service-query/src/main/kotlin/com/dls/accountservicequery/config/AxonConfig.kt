package com.dls.accountservicequery.config

import com.mongodb.ServerAddress
import com.mongodb.client.MongoClient
import org.axonframework.eventhandling.tokenstore.TokenStore
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore
import org.axonframework.eventsourcing.eventstore.EventStorageEngine
import org.axonframework.eventsourcing.eventstore.EventStore
import org.axonframework.extensions.mongo.DefaultMongoTemplate
import org.axonframework.extensions.mongo.MongoTemplate
import org.axonframework.extensions.mongo.eventsourcing.eventstore.MongoEventStorageEngine
import org.axonframework.extensions.mongo.eventsourcing.eventstore.MongoFactory
import org.axonframework.extensions.mongo.eventsourcing.eventstore.MongoSettingsFactory
import org.axonframework.extensions.mongo.eventsourcing.tokenstore.MongoTokenStore
import org.axonframework.serialization.Serializer
import org.axonframework.spring.config.AxonConfiguration
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class AxonConfig {
    @Value("\${spring.data.mongodb.host}")
    private val mongoHost: String? = null

    @Value("\${spring.data.mongodb.port}")
    private val mongoPort = 0

    @Value("\${spring.data.mongodb.database}")
    private val mongoDatabase: String? = null

    @Bean
    fun mongo(): MongoClient {
        val mongoFactory = MongoFactory()
        val mongoSettingsFactory = MongoSettingsFactory()
        mongoSettingsFactory.setMongoAddresses(listOf(ServerAddress(mongoHost, mongoPort)))
        mongoFactory.setMongoClientSettings(mongoSettingsFactory.createMongoClientSettings())
        return mongoFactory.createMongo()
    }

    @Bean
    fun axonMongoTemplate(): MongoTemplate {
        return DefaultMongoTemplate.builder()
            .mongoDatabase(mongo(), mongoDatabase)
            .build()
    }

    @Bean
    fun tokenStore(serializer: Serializer?): TokenStore {
        return MongoTokenStore.builder()
            .mongoTemplate(axonMongoTemplate())
            .serializer(serializer)
            .build()
    }

    @Bean
    fun storageEngine(client: MongoClient?): EventStorageEngine {
        return MongoEventStorageEngine.builder()
            .mongoTemplate(
                DefaultMongoTemplate.builder()
                    .mongoDatabase(client)
                    .build()
            )
            .build()
    }

    @Bean
    fun eventStore(storageEngine: EventStorageEngine?, configuration: AxonConfiguration): EmbeddedEventStore {
        return EmbeddedEventStore.builder()
            .storageEngine(storageEngine)
            .messageMonitor(configuration.messageMonitor(EventStore::class.java, "eventStore"))
            .build()
    }
}

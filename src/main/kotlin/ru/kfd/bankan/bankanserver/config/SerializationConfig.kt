package ru.kfd.bankan.bankanserver.config

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SerializationConfig {

    @Bean
    fun mapper() = jacksonObjectMapper()
}
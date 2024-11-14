/**
 *    Copyright 2019 Sven Loesekann
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at
http://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package de.xxx.hotelselection.adapter.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.eclipse.paho.mqttv5.client.IMqttClient
import org.eclipse.paho.mqttv5.client.MqttClient
import org.eclipse.paho.mqttv5.client.MqttConnectionOptions
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*


@Configuration
class Application {
    @Value("\${mqtt.url:tcp://localhost}")
    private lateinit var mqttUrl: String;
    @Value("\${mqtt.username:artemis1}")
    private lateinit var userName: String
    @Value("\${mqtt.password:artemis1}")
    private lateinit var password: String

    @Bean
    fun createObjectMapper(): ObjectMapper? {
        val objectMapper = ObjectMapper()
        objectMapper.registerModule(JavaTimeModule()).registerModule(KotlinModule.Builder().build())
        return objectMapper
    }

    @Bean
    fun createMqttClient(): IMqttClient {
        val client = MqttClient(this.mqttUrl, UUID.randomUUID().toString())
        val options = MqttConnectionOptions()
        options.setAutomaticReconnect(true)
        options.connectionTimeout = 10
        options.userName = this.userName
        options.password = this.password.toByteArray()

        if (!client.isConnected() && !this.userName.contains("test")) {
            client.connect(options)
        }
        return client
    }
}
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
package de.xxx.hotelselection.adapter.events

import com.fasterxml.jackson.databind.ObjectMapper
import de.xxx.hotelselection.domain.model.entity.Booking
import de.xxx.hotelselection.usecase.mapper.BookingMapper
import jakarta.annotation.PreDestroy
import org.eclipse.paho.mqttv5.client.IMqttClient
import org.eclipse.paho.mqttv5.client.IMqttToken
import org.eclipse.paho.mqttv5.client.MqttCallback
import org.eclipse.paho.mqttv5.client.MqttDisconnectResponse
import org.eclipse.paho.mqttv5.common.MqttException
import org.eclipse.paho.mqttv5.common.MqttMessage
import org.eclipse.paho.mqttv5.common.packet.MqttProperties
import org.slf4j.LoggerFactory
import org.springframework.boot.context.event.ApplicationStartedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.util.*
import java.util.zip.GZIPInputStream
import java.util.zip.GZIPOutputStream


@Component
class MqttProducer(val mqttClient: IMqttClient, val objectMapper: ObjectMapper, val bookingMapper: BookingMapper): MqttCallback {
    private val log = LoggerFactory.getLogger(javaClass)
    private val TOPIC_NAME = "hotel-booking"

    @EventListener(ApplicationStartedEvent::class)
    fun start() {
        this.mqttClient.setCallback(this)
        this.mqttClient.subscribe(this.TOPIC_NAME, 1)
        //This library method gets stuck in a recursive loop -> Stackoverflow
        //this.mqttClient.subscribe(, { topic, event -> log.info("Topic: ${topic}, Value: ${this.gunzip(Base64.getDecoder().decode(event.payload)).toString()}") })
    }

    @PreDestroy
    fun stop() {
        this.mqttClient.unsubscribe(this.TOPIC_NAME)
    }

    fun sendBooking(booking: Booking, deleted: Boolean = false) {
        val event = this.bookingMapper.toDto(booking)
        event.deleted = deleted
        val message = MqttMessage()
        message.qos = 1
        message.payload = Base64.getEncoder()
            .encode(this.gzip(this.objectMapper.writeValueAsString(event)))
        this.mqttClient.publish(this.TOPIC_NAME, message)
    }

    private fun gzip(value: String): ByteArray {
        val os = ByteArrayOutputStream()
        val iStream = ByteArrayInputStream(value.toByteArray())
        val gzipOs = GZIPOutputStream(os)
        var result: ByteArray
        iStream.use {
            gzipOs.use {
                iStream.transferTo(gzipOs)
                result = os.toByteArray()
            }
        }
        return result
    }

    fun gunzip(value: ByteArray): ByteArray {
        val inputStream = GZIPInputStream(ByteArrayInputStream(value))
        val outputStream = ByteArrayOutputStream()
        var result: ByteArray
        inputStream.use {
            outputStream.use {
                inputStream.transferTo(outputStream)
                result = outputStream.toByteArray()
            }
        }
        return result
    }

    override fun disconnected(p0: MqttDisconnectResponse?) {
        log.info("Topic disconnected: ${this.TOPIC_NAME}")
    }

    override fun mqttErrorOccurred(p0: MqttException?) {
        log.info("Mqtt error: ${p0?.message}")
    }

    override fun messageArrived(topic: String?, event: MqttMessage?) {
        log.info("Topic: ${topic}, Value: ${this.gunzip(Base64.getDecoder().decode(event?.payload)).toString()}")
    }

    override fun deliveryComplete(p0: IMqttToken?) {

    }

    override fun connectComplete(p0: Boolean, p1: String?) {

    }

    override fun authPacketArrived(p0: Int, p1: MqttProperties?) {

    }
}
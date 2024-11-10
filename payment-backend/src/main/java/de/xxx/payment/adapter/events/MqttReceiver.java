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
package de.xxx.payment.adapter.events;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.zip.GZIPInputStream;

import org.eclipse.paho.mqttv5.client.IMqttClient;
import org.eclipse.paho.mqttv5.client.IMqttToken;
import org.eclipse.paho.mqttv5.client.MqttCallback;
import org.eclipse.paho.mqttv5.client.MqttDisconnectResponse;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.eclipse.paho.mqttv5.common.MqttMessage;
import org.eclipse.paho.mqttv5.common.packet.MqttProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.xxx.payment.domain.model.dto.FlightDto;
import de.xxx.payment.domain.model.dto.HotelDto;
import de.xxx.payment.usecase.mapper.FlightMapper;
import de.xxx.payment.usecase.mapper.HotelMapper;
import de.xxx.payment.usecase.service.FlightService;
import de.xxx.payment.usecase.service.HotelService;
import jakarta.annotation.PreDestroy;

@Component
public class MqttReceiver implements MqttCallback {
	private static final Logger LOG = LoggerFactory.getLogger(MqttReceiver.class);
	private static final String HOTEL_TOPIC_NAME = "hotel-booking";
	private static final String FLIGHT_TOPIC_NAME = "flight-booking";
	private final ObjectMapper objectMapper;
	private final IMqttClient mqttClient;
	private final FlightMapper flightMapper;
	private final HotelMapper hotelMapper;
	private final FlightService flightService;
	private final HotelService hotelService;

	public MqttReceiver(IMqttClient mqttClient, ObjectMapper objectMapper, FlightMapper flightMapper,
			HotelMapper hotelMapper, FlightService flightService, HotelService hotelService) {
		this.objectMapper = objectMapper;
		this.mqttClient = mqttClient;
		this.flightMapper = flightMapper;
		this.hotelMapper = hotelMapper;
		this.flightService = flightService;
		this.hotelService = hotelService;
	}

	@EventListener(ApplicationStartedEvent.class)
	public void start() {
		this.mqttClient.setCallback(this);
		try {
			this.mqttClient.subscribe(new String[] { HOTEL_TOPIC_NAME, FLIGHT_TOPIC_NAME}, new int[] {1,1});
		} catch (MqttException e) {
			LOG.error("Mqtt error: ", e);
		}
	}

	@PreDestroy
	public void stop() {
		try {
			this.mqttClient.disconnect();
		} catch (MqttException e) {
			LOG.error("Mqtt error: ", e);
		}
	}

	private byte[] gunzip(byte[] value) {
		var result = new byte[0];
		try (var outputStream = new ByteArrayOutputStream()) {
			try (var inputStream = new GZIPInputStream(new ByteArrayInputStream(value))) {
				inputStream.transferTo(outputStream);
			}
			result = outputStream.toByteArray();
		} catch (IOException e) {
			LOG.error("Gzip: ", e);
		}
		return result;
	}

	@Override
	public void disconnected(MqttDisconnectResponse disconnectResponse) {
		LOG.warn("Mqtt: ", disconnectResponse.getReasonString());
	}

	@Override
	public void mqttErrorOccurred(MqttException exception) {
		LOG.error("Mqtt: ", exception);
	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		var jsonStr = new String(this.gunzip(Base64.getDecoder().decode(message.getPayload())));
//		LOG.info(String.format("Topic: %s, value: %s", topic, jsonStr));
		switch (topic) {
		case HOTEL_TOPIC_NAME -> {
			handleHotelEvent(topic, jsonStr);
		}
		case FLIGHT_TOPIC_NAME -> {
			handleFlightEvent(topic, jsonStr);
		}
		}
	}

	private void handleFlightEvent(String topic, String jsonStr) throws JsonProcessingException, JsonMappingException {
		var dto = this.objectMapper.readValue(jsonStr, FlightDto.class);
		var entity = this.flightMapper.toEntity(dto, false);
		if(!dto.deleted()) {
			entity = this.flightService.book(entity);
		} else {
			this.flightService.cancel(entity.getId());
		}
		LOG.info(String.format("Topic: %s, value: %s", topic, entity.toString()));
	}

	private void handleHotelEvent(String topic, String jsonStr) throws JsonProcessingException, JsonMappingException {
		var dto = this.objectMapper.readValue(jsonStr, HotelDto.class);
		var entity = this.hotelMapper.toEntity(dto, false);
		if (!dto.deleted()) {
			entity = this.hotelService.book(entity);
		} else {
			this.hotelService.cancel(entity.getId());
		}
		LOG.info(String.format("Topic: %s, value: %s", topic, entity.toString()));
	}

	@Override
	public void deliveryComplete(IMqttToken token) {
		// TODO Auto-generated method stub

	}

	@Override
	public void connectComplete(boolean reconnect, String serverURI) {
		// TODO Auto-generated method stub

	}

	@Override
	public void authPacketArrived(int reasonCode, MqttProperties properties) {
		// TODO Auto-generated method stub

	}
}

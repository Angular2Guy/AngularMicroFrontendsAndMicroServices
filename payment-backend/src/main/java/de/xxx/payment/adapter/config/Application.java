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
package de.xxx.payment.adapter.config;

import java.util.UUID;

import org.eclipse.paho.mqttv5.client.IMqttClient;
import org.eclipse.paho.mqttv5.client.MqttClient;
import org.eclipse.paho.mqttv5.client.MqttConnectionOptions;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class Application {
	private static final Logger LOG = LoggerFactory.getLogger(Application.class);
	@Value("${mqtt.url:tcp://localhost}")
	private String mqttUrl;
	@Value("${mqtt.username:artemis1}")
	private String userName;
	@Value("${mqtt.password:artemis1}")
	private String password;

	@Bean
	public ObjectMapper createObjectMapper() {
		var objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		return objectMapper;
	}
	
	@Bean
	public IMqttClient createMqttClient() {
		MqttClient client = null;
		try {
			client = new MqttClient(this.mqttUrl, UUID.randomUUID().toString());
			var options = new MqttConnectionOptions();
			options.setAutomaticReconnect(true);
			options.setConnectionTimeout(10);
			options.setUserName(this.userName);
			options.setPassword(this.password.getBytes());
			if (!client.isConnected()) {
				client.connect(options);
			}
		} catch (MqttException e) {
			LOG.error("Mqtt error: ", e);
		}
		return client;
	}
}

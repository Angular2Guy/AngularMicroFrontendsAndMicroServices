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
package de.xxx.payment.usecase.mapper;

import java.util.UUID;

import org.springframework.stereotype.Component;

import de.xxx.payment.domain.model.dto.FlightDto;
import de.xxx.payment.domain.model.entity.Flight;

@Component
public class FlightMapper {

	public Flight toEntity(FlightDto dto, boolean paid) {
		var entity = new Flight(UUID.fromString(dto.id()), dto.flightDate(), dto.airline(), dto.fromCity(),
				dto.toCity(), dto.price(), paid);
		return entity;
	}

	public FlightDto toDto(Flight flight, boolean deleted) {
		var dto = new FlightDto(flight.getId().toString(), flight.getFlightDate(), flight.getAirline(),
				flight.getFromCity(), flight.getToCity(), flight.getPrice(), deleted);
		return dto;
	}
}

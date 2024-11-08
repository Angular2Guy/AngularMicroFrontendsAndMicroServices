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

import de.xxx.payment.domain.model.dto.HotelDto;
import de.xxx.payment.domain.model.entity.Hotel;

@Component
public class HotelMapper {
	public HotelDto toDto(Hotel hotel, boolean deleted) {
		var dto = new HotelDto(hotel.getId().toString(), hotel.getFromDate(), hotel.getToDate(), hotel.getHotelName(),
				hotel.getCity(), hotel.getPrice(), deleted);
		return dto;
	}

	public Hotel toEntity(HotelDto hotelDto, boolean paid) {
		var entity = new Hotel(UUID.fromString(hotelDto.id()), hotelDto.fromDate(), hotelDto.toDate(),
				hotelDto.hotelName(), hotelDto.city(), hotelDto.price(), paid);
		return entity;
	}
}

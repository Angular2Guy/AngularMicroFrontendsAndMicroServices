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
package de.xxx.payment.adapter.controller;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.xxx.payment.domain.model.dto.HotelDto;
import de.xxx.payment.usecase.mapper.HotelMapper;
import de.xxx.payment.usecase.service.HotelService;

@RestController
@RequestMapping("/rest/hotel")
public class HotelController {
	private final HotelMapper hotelMapper;
	private final HotelService hotelService;

	public HotelController(HotelMapper hotelMapper, HotelService hotelService) {
		this.hotelMapper = hotelMapper;
		this.hotelService = hotelService;
	}

	@GetMapping("/open")
	public Collection<HotelDto> getOpenHotels() {
		return this.hotelService.findUnpaidHotels().stream().map(myHotel -> this.hotelMapper.toDto(myHotel, false))
				.toList();
	}

	@PostMapping("/pay")
	public List<HotelDto> postPayHotels(@RequestBody List<UUID> ids) {
		return this.hotelService.pay(ids).stream().map(myHotel -> this.hotelMapper.toDto(myHotel, true)).toList();
	}
}

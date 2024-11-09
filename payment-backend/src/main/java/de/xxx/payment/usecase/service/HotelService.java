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
package de.xxx.payment.usecase.service;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import de.xxx.payment.domain.model.entity.Hotel;
import de.xxx.payment.domain.model.entity.HotelRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class HotelService {
	private final HotelRepository hotelRepository;

	public HotelService(HotelRepository hotelRepository) {
		this.hotelRepository = hotelRepository;
	}

	public Hotel book(Hotel hotel) {
		return this.hotelRepository.save(hotel);
	}

	public Collection<Hotel> findUnpaidHotels() {
		return StreamSupport.stream(this.hotelRepository.findByPaid(false).spliterator(), false)
				.collect(Collectors.toList());
	}

	public void cancel(UUID id) {
		this.hotelRepository.deleteById(id);
	}

	public Collection<Hotel> pay(List<UUID> ids) {
		return StreamSupport.stream(this.hotelRepository.findAllById(ids).spliterator(), false).map(myHotel -> {
			myHotel.setPaid(true);
			return myHotel;
		}).map(myHotel -> this.hotelRepository.save(myHotel)).toList();
	}
}

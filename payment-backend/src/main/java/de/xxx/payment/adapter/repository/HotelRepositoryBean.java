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
package de.xxx.payment.adapter.repository;

import java.util.Optional;
import java.util.UUID;

import de.xxx.payment.domain.model.entity.Hotel;
import de.xxx.payment.domain.model.entity.HotelRepository;

public class HotelRepositoryBean implements HotelRepository {
	private final JpaHotelRepository jpaHotelRepository;
	
	public HotelRepositoryBean(JpaHotelRepository jpaHotelRepository) {
		this.jpaHotelRepository = jpaHotelRepository;
	}
	
	@Override
	public Hotel save(Hotel hotel) {
		return this.jpaHotelRepository.save(hotel);
	}
	
	@Override
	public Optional<Hotel> findById(UUID id) {
		return this.jpaHotelRepository.findById(id);
	}
	
	@Override
	public Iterable<Hotel> findByPaid(boolean paid) {
		return this.jpaHotelRepository.findByPaid(paid);
	}
	
	@Override
	public void deleteById(UUID id) {
		this.jpaHotelRepository.deleteById(id);
	}
}

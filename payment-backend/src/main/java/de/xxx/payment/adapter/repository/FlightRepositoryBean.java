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

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import de.xxx.payment.domain.model.entity.Flight;
import de.xxx.payment.domain.model.entity.FlightRepository;

@Repository
public class FlightRepositoryBean implements FlightRepository {
	private final JpaFlightRepository jpaFlightRepository;
	
	public FlightRepositoryBean(JpaFlightRepository jpaFlightRepository) {
		this.jpaFlightRepository = jpaFlightRepository;
	}
	
	@Override
	public Flight save(Flight flight) {
		return this.jpaFlightRepository.save(flight);
	}
	
	@Override
	public Optional<Flight> findById(UUID id) {
		return this.jpaFlightRepository.findById(id);
	}
	
	@Override
	public Iterable<Flight> findByPaid(boolean paid) {
		return this.jpaFlightRepository.findByPaid(paid);
	}
	
	@Override
	public Iterable<Flight> findAllById(List<UUID> ids) {
		return this.jpaFlightRepository.findAllById(ids);
	}

	@Override
	public void deleteById(UUID id) {
		this.jpaFlightRepository.deleteById(id);
	}
}

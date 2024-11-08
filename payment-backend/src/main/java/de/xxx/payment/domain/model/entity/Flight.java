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
package de.xxx.payment.domain.model.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Flight {
	@Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private LocalDate flightDate;
    private String airline;
    private String fromCity;
    private String toCity;
    private BigDecimal price;
    private Boolean paid = false;
    
    public Flight() {
    	
    }
    
	public Flight(UUID id, LocalDate flightDate, String airline, String fromCity, String toCity, BigDecimal price, Boolean paid) {
		super();
		this.id = id;
		this.flightDate = flightDate;
		this.airline = airline;
		this.fromCity = fromCity;
		this.toCity = toCity;
		this.price = price;
		this.paid = paid;
	}

	public Boolean getPaid() {
		return paid;
	}

	public void setPaid(Boolean paid) {
		this.paid = paid;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public LocalDate getFlightDate() {
		return flightDate;
	}

	public void setFlightDate(LocalDate flightDate) {
		this.flightDate = flightDate;
	}

	public String getAirline() {
		return airline;
	}

	public void setAirline(String airline) {
		this.airline = airline;
	}

	public String getFromCity() {
		return fromCity;
	}

	public void setFromCity(String fromCity) {
		this.fromCity = fromCity;
	}

	public String getToCity() {
		return toCity;
	}

	public void setToCity(String toCity) {
		this.toCity = toCity;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Flight [id=" + id + ", flightDate=" + flightDate + ", airline=" + airline + ", fromCity=" + fromCity
				+ ", toCity=" + toCity + ", price=" + price + ", paid=" + paid + "]";
	}	
}
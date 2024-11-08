package de.xxx.payment.domain.model.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Hotel {
	@Id @GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	private LocalDate fromDate;
	private LocalDate toDate;
	private String hotelName;
	private String city;
	private BigDecimal price;
	private Boolean paid = false;
	
	public Hotel() { }

	public Hotel(UUID id, LocalDate fromDate, LocalDate toDate, String hotelName, String city, BigDecimal price, Boolean paid) {
		super();
		this.id = id;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.hotelName = hotelName;
		this.city = city;
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

	public LocalDate getFromDate() {
		return fromDate;
	}

	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}

	public LocalDate getToDate() {
		return toDate;
	}

	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Hotel [id=" + id + ", fromDate=" + fromDate + ", toDate=" + toDate + ", hotelName=" + hotelName
				+ ", city=" + city + ", price=" + price + ", paid=" + paid + "]";
	}
}

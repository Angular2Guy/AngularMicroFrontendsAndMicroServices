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
package de.xxx.hotelselection.usecase.service

import de.xxx.hotelselection.adapter.events.MqttProducer
import de.xxx.hotelselection.domain.model.entity.Booking
import de.xxx.hotelselection.domain.model.entity.BookingRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class BookingService(val bookingRepository: BookingRepository, val mqttProducer: MqttProducer) {
    fun saveBooking(booking: Booking): Booking {
        val result = this.bookingRepository.save(booking)
        this.mqttProducer.sendBooking(result)
        return result
    }

    fun findByHotelId(hotelId: UUID): Set<Booking> {
        return this.bookingRepository.findByHotelId(hotelId)
    }

    fun deleteBooking(bookingId: UUID): Boolean {
        this.bookingRepository.findById(bookingId).ifPresent({
            this.bookingRepository.deleteBooking(bookingId)
            this.mqttProducer.sendBooking(it, true)
        })
        return true
    }
}
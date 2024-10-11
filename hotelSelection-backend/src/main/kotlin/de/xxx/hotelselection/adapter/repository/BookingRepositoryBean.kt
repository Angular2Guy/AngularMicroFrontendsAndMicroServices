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
package de.xxx.hotelselection.adapter.repository

import de.xxx.hotelselection.domain.model.entity.Booking
import de.xxx.hotelselection.domain.model.entity.BookingRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class BookingRepositoryBean(val jpaBookingRepository: JpaBookingRepository): BookingRepository {
    override fun save(booking: Booking): Booking {
        return this.jpaBookingRepository.save(booking)
    }

    override fun findByHotelId(hotelId: UUID): Set<Booking> {
        return this.jpaBookingRepository.findByHotelId(hotelId)
    }

    override fun deleteBooking(id: UUID) {
        return this.jpaBookingRepository.deleteById(id)
    }
}
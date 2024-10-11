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
package de.xxx.hotelselection.adapter.controller

import de.xxx.hotelselection.domain.model.dto.BookingDto
import de.xxx.hotelselection.usecase.mapper.BookingMapper
import de.xxx.hotelselection.usecase.service.BookingService
import de.xxx.hotelselection.usecase.service.HotelService
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("rest/booking")
class BookingController(
    val bookingMapper: BookingMapper,
    val hotelService: HotelService,
    val bookingService: BookingService
) {

    @PostMapping("/hotel/{id}")
    fun postBooking(@PathVariable("id") id: String, @RequestBody bookingDto: BookingDto): BookingDto {
        return this.bookingMapper.toDto(
            this.bookingService.saveBooking(
                this.bookingMapper.toEntity(
                    bookingDto,
                    this.hotelService.findHotelById(UUID.fromString(id)).orElseThrow()
                )
            )
        )
    }

    @GetMapping("/hotel/{id}")
    fun getBookingsForHotel(@PathVariable("id") id: String): Set<BookingDto> {
        return this.bookingMapper.toDtos(this.bookingService.findByHotelId(UUID.fromString(id)))
    }

    @DeleteMapping("/id/{id}")
    fun deleteBooking(@PathVariable("id") id: String): Boolean {
        return this.bookingService.deleteBooking(UUID.fromString(id))
    }
}
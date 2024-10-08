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

import de.xxx.hotelselection.domain.model.dto.HotelDto
import de.xxx.hotelselection.usecase.mapper.HotelMapper
import de.xxx.hotelselection.usecase.service.HotelService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import java.util.*

@RestController
@RequestMapping("rest/hotel")
class HotelController(val hotelService: HotelService, val hotelMapper: HotelMapper) {

    @GetMapping("/city/{city}")
    fun getHotelsForCity(@PathVariable city: String): List<HotelDto> {
        return this.hotelService.findHotelsInCity(city).map { this.hotelMapper.toHotelDto(it) }
    }

    @GetMapping("/cities")
    fun getCitiesWithHotels(): List<String> {
        return this.hotelService.findCitiesWithHotels()
    }

    @GetMapping("/id/{id}")
    fun getHotelById(@PathVariable id: String): HotelDto {
        return this.hotelService.findHotelById(UUID.fromString(id)).map { this.hotelMapper.toHotelDto(it) }.orElseThrow()
    }
}
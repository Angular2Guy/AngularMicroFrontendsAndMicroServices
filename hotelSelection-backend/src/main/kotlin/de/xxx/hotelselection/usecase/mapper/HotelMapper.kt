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
package de.xxx.hotelselection.usecase.mapper

import de.xxx.hotelselection.domain.model.dto.HotelDto
import de.xxx.hotelselection.domain.model.entity.Hotel
import org.springframework.stereotype.Component
import java.util.*

@Component
class HotelMapper {
    fun toDto(hotel: Hotel): HotelDto {
        return HotelDto(hotel.id.toString(), hotel.hotelName, hotel.city)
    }

    fun toEntity(hotelDto: HotelDto): Hotel {
        return Hotel(Optional.ofNullable(hotelDto.id).stream().map { UUID.fromString(it) }.findFirst().orElse(null), hotelDto.hotelName, hotelDto.city)
    }
}
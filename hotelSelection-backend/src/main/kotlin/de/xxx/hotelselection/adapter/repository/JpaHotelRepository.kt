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

import de.xxx.hotelselection.domain.model.entity.Hotel
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.query.Param
import java.util.UUID

interface JpaHotelRepository: PagingAndSortingRepository<Hotel, UUID>, CrudRepository<Hotel, UUID> {
    @Query("select h from Hotel h where lower(h.city) like lower(concat('%', :city,'%'))")
    fun findByCity(@Param("city") city: String): List<Hotel>

    @Query("select distinct h.city from Hotel h")
    fun findCitiesWithHotels(): List<String>
}
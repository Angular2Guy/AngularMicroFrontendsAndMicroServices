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
package de.xxx.hotelselection.domain.model.entity

import jakarta.persistence.*
import java.time.LocalDate
import java.util.UUID

@Entity
class Booking(
    @Id @GeneratedValue(strategy = GenerationType.UUID) var id: UUID?,
    @ManyToOne val hotel: Hotel,
    @Column(name = "from_date") val from: LocalDate,
    @Column(name = "to_date") val to: LocalDate
) {
}
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
import { Injectable } from "@nestjs/common";
import { BookingDto } from "src/domain/dto/booking-dto";
import { Booking } from "src/domain/entity/booking";
import { FlightMapper } from "./flight-mapper.service";

@Injectable()
export class BookingMapper {
    constructor(private flightMapper: FlightMapper) { }

    public toDto(booking: Booking): BookingDto {
        return new BookingDto(booking.id, booking.flightDate, this.flightMapper.toDto(booking.flight));
    }

    public toEntity(bookingDto: BookingDto): Booking {
        const entity = new Booking();
        entity.flightDate = bookingDto.flightDate;
        entity.id = bookingDto.id;        
        return entity;
    }
}
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
import { FlightDto } from "src/domain/dto/flight-dto";
import { Flight } from "src/domain/entity/flight";

@Injectable()
export class FlightMapper {
    public toDto(flight: Flight): FlightDto | undefined {
        return !flight ? undefined : new FlightDto(flight.id, flight.flightNumber, flight.airline, flight.fromCity, flight.toCity, flight.price);
    }

    public toEntity(flightDto: FlightDto): Flight {
        const entity = new Flight();
        entity.airline = flightDto.airline;
        entity.flightNumber = flightDto.flightNumber;
        entity.fromCity = flightDto.fromCity;
        entity.id = flightDto.id;
        entity.toCity = flightDto.toCity;
        entity.price = flightDto.price;
        return entity;
    }
}
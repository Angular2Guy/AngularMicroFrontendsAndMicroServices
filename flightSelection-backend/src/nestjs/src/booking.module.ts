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
import { Module } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import { Booking } from './domain/entity/booking';
import { BookingController } from './adapter/controller/booking.controller';
import { BookingService } from './usecase/service/booking.service';
import { BookingMapper } from './usecase/mapper/booking-mapper.service';
import { FlightMapper } from './usecase/mapper/flight-mapper.service';
import { Flight } from './domain/entity/flight';

@Module({
  imports: [TypeOrmModule.forFeature([Booking]),TypeOrmModule.forFeature([Flight])],
  exports: [TypeOrmModule],
  controllers: [BookingController],
  providers: [BookingService, BookingMapper, FlightMapper],
})
export class BookingModule {}
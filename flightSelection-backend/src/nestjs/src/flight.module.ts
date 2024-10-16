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
import { FlightController } from './adapter/controller/flight.controller';
import { FlightService } from './usecase/service/flight.service';
import { TypeOrmModule } from '@nestjs/typeorm';
import { Flight } from './domain/entity/flight';
import { FlightMapper } from './usecase/mapper/flight-mapper.service';

@Module({
  imports: [TypeOrmModule.forFeature([Flight])],
  exports: [TypeOrmModule],
  controllers: [FlightController],
  providers: [FlightService, FlightMapper],
})
export class FlightModule {}
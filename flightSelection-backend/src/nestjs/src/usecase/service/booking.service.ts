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
import { Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { MqttProducerService } from 'src/adapter/events/mqtt-producer.service';
import { Booking } from 'src/domain/entity/booking';
import { Flight } from 'src/domain/entity/flight';
import { FindOptionsWhere, Repository } from 'typeorm';

@Injectable()
export class BookingService {
  constructor(
    @InjectRepository(Booking)
    private bookingRepository: Repository<Booking>,
    @InjectRepository(Flight)
    private flightRepository: Repository<Flight>,
    private mqttProducerService: MqttProducerService
  ) {}
  
  getAllBookings(): Promise<Booking[]> {
    return this.bookingRepository.find({relations: {flight: true}});
  }

  saveBooking(flightId: string, booking: Booking): Promise<Booking> {
    return this.flightRepository.findBy({id: flightId}).then(myFlight => {     
      booking.flight = myFlight?.length > 0 ? myFlight[0] : booking.flight;      
      return this.bookingRepository.save(booking).then(value => {this.mqttProducerService.sendBooking(value); return value;});
    });    
  }

  deleteBooking(bookingId: string): Promise<boolean> {
    return this.bookingRepository.findBy({id: bookingId} as FindOptionsWhere<Booking>).then(value => {
      if(!!value && !!value[0]) {
      this.mqttProducerService.sendBooking(value[0],true);
      }
      return this.bookingRepository.delete(bookingId);
    }).then(result => !!result);
  }
}

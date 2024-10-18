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
import { Body, Controller, Delete, Get, Param, Post } from '@nestjs/common';
import { BookingService } from 'src/usecase/service/booking.service';
import { BookingMapper } from 'src/usecase/mapper/booking-mapper.service';
import { BookingDto } from 'src/domain/dto/booking-dto';

@Controller('rest/bookings')
export class BookingController {
  constructor(private readonly bookingService: BookingService, private readonly bookingMapper: BookingMapper) {}

  @Get('/all')
  getAllBookings(): Promise<BookingDto[]> {
    return this.bookingService.getAllBookings().then(entities => entities.map(entity => this.bookingMapper.toDto(entity)));
  }

  @Post('/book/:id')
  postBooking(@Param('id') flightId: string, @Body() bookingDto: BookingDto): Promise<BookingDto> {
    return this.bookingService.saveBooking(flightId, this.bookingMapper.toEntity(bookingDto)).then(myEntity => this.bookingMapper.toDto(myEntity));
  }

  @Delete('/id/:id')
  deleteBooking(@Param('id') bookingId: string): Promise<boolean> {
    return this.bookingService.deleteBooking(bookingId);
  }
}

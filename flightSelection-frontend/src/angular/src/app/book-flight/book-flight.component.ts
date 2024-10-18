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
import { Component, OnInit } from '@angular/core';
import { Flight } from '../model/flight';
import { FlightService } from '../services/flight.service';
import { ActivatedRoute } from '@angular/router';
import { Booking } from '../model/booking';
import { BookingService } from '../services/booking.service';
import { JsonPipe } from '@angular/common';

@Component({
  selector: 'app-book-flight',
  standalone: true,
  imports: [JsonPipe],
  templateUrl: './book-flight.component.html',
  styleUrl: './book-flight.component.scss'
})
export class BookFlightComponent implements OnInit {
  protected selFlight: Flight | null = null;
  protected bookings: Booking[] = [];

  constructor(private bookingService: BookingService, private flightService: FlightService, private activatedRoute: ActivatedRoute) {  }
  
  ngOnInit(): void {
    this.flightService.getFlightById(this.activatedRoute.snapshot.params['id']).subscribe(result => this.selFlight = result);
    this.bookingService.getAllBookings().subscribe(result => this.bookings = result);
  }


}

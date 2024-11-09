/**
 *    Copyright 2023 Sven Loesekann
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
import { HotelService } from '../service/hotel.service';
import { FlightService } from '../service/flight.service';
import { Flight } from '../model/flight';
import { Hotel } from '../model/hotel';
import { JsonPipe } from '@angular/common';

@Component({
  selector: 'app-payment',
  standalone: true,
  imports: [JsonPipe],
  templateUrl: './payment.component.html',
  styleUrl: './payment.component.scss'
})
export class PaymentComponent implements OnInit {
	protected flights: Flight[] = [];
	protected hotels: Hotel[] = [];
	
	constructor(private hotelService: HotelService, private flightService: FlightService) { }
    
	ngOnInit(): void {
      this.flightService.getFlights().subscribe(result => this.flights = result);
	  this.hotelService.getHotels().subscribe(result => this.hotels = result)
    }
}

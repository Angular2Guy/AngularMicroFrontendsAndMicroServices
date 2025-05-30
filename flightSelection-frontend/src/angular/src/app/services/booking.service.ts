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
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Booking } from '../model/booking';

@Injectable({
  providedIn: 'root'
})
export class BookingService {
  constructor(private httpClient: HttpClient) { }

  getAllBookings(): Observable<Booking[]> {
    return this.httpClient.get<Booking[]>('/rest/bookings/all');
  }

  postBooking(flightId: string, booking: Booking): Observable<Booking> {
    return this.httpClient.post<Booking>(`/rest/bookings/book/${flightId}`, booking);
  }

  deleteBooking(bookingId: string): Observable<boolean> {
    return this.httpClient.delete<boolean>(`/rest/bookings/id/${bookingId}`);
  }
}

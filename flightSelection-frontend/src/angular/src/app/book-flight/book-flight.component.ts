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
import { ActivatedRoute, Router } from '@angular/router';
import { Booking } from '../model/booking';
import { BookingService } from '../services/booking.service';
import { DatePipe, JsonPipe } from '@angular/common';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';
import {MatDatepickerModule} from '@angular/material/datepicker'; 
import {MatIconModule} from '@angular/material/icon'; 
import { provideNativeDateAdapter } from '@angular/material/core';
import { MatInputModule } from '@angular/material/input';
import { mergeMap } from 'rxjs';
import { TranslocoPipe } from '@jsverse/transloco';
import { TranslocoLocaleModule } from '@jsverse/transloco-locale';

enum ControlName {Day = 'day'};

@Component({
    selector: 'app-book-flight',
    imports: [ReactiveFormsModule, MatFormFieldModule, MatDatepickerModule, JsonPipe, MatButtonModule, DatePipe, MatIconModule, MatInputModule, TranslocoPipe, TranslocoLocaleModule],
    templateUrl: './book-flight.component.html',
    styleUrl: './book-flight.component.scss'
})
export class BookFlightComponent implements OnInit {
  protected selFlight: Flight | null = null;
  protected bookings: Booking[] = [];
  protected ControlName = ControlName;
  protected readonly formGroup = new FormGroup({
    [ControlName.Day]: new FormControl<Date | null>(null)
  });

  constructor(private bookingService: BookingService, private flightService: FlightService, private activatedRoute: ActivatedRoute, private router: Router) {  }
  
  ngOnInit(): void {
    this.flightService.getFlightById(this.activatedRoute.snapshot.params['id']).subscribe(result => this.selFlight = result);
    this.bookingService.getAllBookings().subscribe(result => this.bookings = result);
  }

  protected bookFlight(): void {    
    !!this.selFlight?.id && this.bookingService.postBooking(this.selFlight.id, {id: null, flightDate: this.formGroup.controls[ControlName.Day].value?.toISOString() } as Booking).pipe(mergeMap(() => this.bookingService.getAllBookings())).subscribe(result => this.bookings = result);
  }

  protected deleteBooking(booking: Booking): void {
    !!booking?.id && this.bookingService.deleteBooking(booking.id).pipe(mergeMap(() => this.bookingService.getAllBookings())).subscribe(result => this.bookings = result);
  }

  protected cancel(): void {
    this.router.navigate(['/']);
  }
}



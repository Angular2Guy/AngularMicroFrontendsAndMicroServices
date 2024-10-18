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
import {FormGroup, FormControl, ReactiveFormsModule} from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import {MatDatepickerModule} from '@angular/material/datepicker'; 
import {provideNativeDateAdapter} from '@angular/material/core';
import { HotelService } from '../services/hotel.service';
import { Hotel } from '../model/hotel';
import { BookingService } from '../services/booking.service';
import { ActivatedRoute, Router } from '@angular/router';
import { JsonPipe, DatePipe } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { map, mergeMap, Observable } from 'rxjs';
import { Booking } from '../model/booking';
import {MatIconModule} from '@angular/material/icon'; 
import {MatInputModule} from '@angular/material/input';

interface HotelBooking {
  id: string;
  from: Date;
  to: Date;
}

enum ControlName {From = 'from',To='to'};

@Component({
  selector: 'app-book-hotel',
  standalone: true,
  imports: [ReactiveFormsModule,MatFormFieldModule, MatDatepickerModule, JsonPipe,MatButtonModule, DatePipe,MatIconModule, MatInputModule],
  providers: [provideNativeDateAdapter()],
  templateUrl: './book-hotel.component.html',
  styleUrl: './book-hotel.component.scss'
})
export class BookHotelComponent implements OnInit {
  protected selHotel: Hotel | null = null;
  protected bookings: HotelBooking[] = [];
  protected readonly formGroup = new FormGroup({
    [ControlName.From]: new FormControl<Date | null>(null),
    [ControlName.To]: new FormControl<Date | null>(null),
  });
  protected ControlName = ControlName;

  constructor(private hotelService: HotelService, private bookingService: BookingService,private activatedRoute: ActivatedRoute, private router: Router) { }

  ngOnInit(): void {    
    this.hotelService.getHotel(this.activatedRoute.snapshot.params['id']).subscribe(result => this.selHotel = result);
    this.readBookings(this.activatedRoute.snapshot.params['id']).subscribe(result => this.bookings = result);
  }

  private readBookings(id: string): Observable<HotelBooking[]> {
    return this.bookingService.getBookings(id).pipe(map(myValue => myValue.map(value => ({id: value.id, from: new Date(value.from), to: new Date(value.to) } as HotelBooking))));
  }

  protected bookHotel(): void {
    !!this.selHotel?.id && this.formGroup.valid && this.bookingService.postBooking(this.selHotel?.id || '', {id: null, from: this.formGroup.controls[ControlName.From]?.value?.toISOString(), to: this.formGroup.controls[ControlName.To]?.value?.toISOString() } as Booking).pipe(mergeMap(() => this.readBookings(this.selHotel?.id || ''))).subscribe(result => this.bookings = result)
  }

  protected deleteBooking(booking: HotelBooking): void {
    !!booking.id && this.bookingService.deleteBooking(booking.id).pipe(mergeMap(() => this.readBookings(this.selHotel?.id || ''))).subscribe(result => this.bookings = result);
  }

  protected cancel(): void {
    this.router.navigate(['/']);
  }
}

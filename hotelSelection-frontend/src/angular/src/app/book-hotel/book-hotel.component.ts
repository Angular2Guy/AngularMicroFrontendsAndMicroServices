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
import { ActivatedRoute } from '@angular/router';
import { JsonPipe } from '@angular/common';


@Component({
  selector: 'app-book-hotel',
  standalone: true,
  imports: [ReactiveFormsModule,MatFormFieldModule, MatDatepickerModule, JsonPipe],
  providers: [provideNativeDateAdapter()],
  templateUrl: './book-hotel.component.html',
  styleUrl: './book-hotel.component.scss'
})
export class BookHotelComponent implements OnInit {
  protected selHotel: Hotel | null = null;
  protected readonly range = new FormGroup({
    start: new FormControl<Date | null>(null),
    end: new FormControl<Date | null>(null),
  });

  constructor(private hotelService: HotelService, private bookingService: BookingService,private activatedRoute: ActivatedRoute) { }

  ngOnInit(): void {
    this.hotelService.getHotel(this.activatedRoute.snapshot.params['id']).subscribe(result => this.selHotel = result);
  }
}

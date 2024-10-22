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
import {MatSelectModule} from '@angular/material/select'; 
import {MatButtonModule} from '@angular/material/button'; 
import { HotelService } from '../services/hotel.service';
import { debounceTime, switchMap } from 'rxjs';
import { Hotel } from '../model/hotel';
import { Router } from '@angular/router';
import { TranslocoPipe } from '@jsverse/transloco';

enum ControlName {City = "city", Hotel="hotel"}

@Component({
  selector: 'app-select-hotel',
  standalone: true,
  imports: [ReactiveFormsModule,MatSelectModule,MatButtonModule,TranslocoPipe],
  templateUrl: './select-hotel.component.html',
  styleUrl: './select-hotel.component.scss'
})
export class SelectHotelComponent implements OnInit {  
  protected formGroup = new FormGroup({
    [ControlName.City]: new FormControl(''),
    [ControlName.Hotel]: new FormControl('')
  });
  protected cities: String[] = [];
  protected hotels: Hotel[] = [];
  protected ControlName = ControlName;

  constructor(private hotelService: HotelService, private router: Router) { }

  ngOnInit(): void {
    this.hotelService.getCities().subscribe(result => this.cities = result);
    this.formGroup.controls[ControlName.City].valueChanges.pipe(debounceTime(300), switchMap(value => this.hotelService.getHotels(value || ''))).subscribe(result => this.hotels = result);
  }

  bookHotel(): void {
    this.router.navigate(['bookhotel', this.formGroup.controls[ControlName.Hotel].value]);
  }
}

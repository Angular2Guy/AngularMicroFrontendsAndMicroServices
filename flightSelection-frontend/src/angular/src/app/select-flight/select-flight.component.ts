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
import { FlightService } from '../services/flight.service';
import { Flight } from '../model/flight';
import { JsonPipe } from '@angular/common';
import {FormGroup, FormControl, ReactiveFormsModule} from '@angular/forms';
import {MatSelectModule} from '@angular/material/select'; 
import {MatButtonModule} from '@angular/material/button';
import { Router } from '@angular/router';
import { TranslocoPipe } from '@jsverse/transloco';

enum ControlName {FromTo = "fromTo"}

@Component({
  selector: 'app-select-flight',
  standalone: true,
  imports: [ReactiveFormsModule,MatSelectModule,MatButtonModule,JsonPipe, TranslocoPipe],
  templateUrl: './select-flight.component.html',
  styleUrl: './select-flight.component.scss'
})
export class SelectFlightComponent implements OnInit{
  protected formGroup = new FormGroup({
    [ControlName.FromTo]: new FormControl('')    
  });
  protected flights: Flight[] = [];
  protected ControlName = ControlName;

  constructor(private flightService: FlightService, private router: Router) { }
  
  ngOnInit(): void {
    this.flightService.getAllFlights().subscribe(result => this.flights = result);
  }

  bookFlight(): void {
    console.log(this.formGroup.controls[ControlName.FromTo].value);
    this.router.navigate(['bookflight', this.formGroup.controls[ControlName.FromTo].value])
  }
}

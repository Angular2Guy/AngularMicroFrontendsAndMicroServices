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
import { Component } from '@angular/core';
import {MatButtonModule} from '@angular/material/button'; 
import { Router } from '@angular/router';
import {DomSanitizer} from '@angular/platform-browser';

@Component({
  selector: 'app-booking',
  standalone: true,
  imports: [MatButtonModule],
  templateUrl: './booking.component.html',
  styleUrl: './booking.component.scss'
})
export class BookingComponent {
  protected iframeUrl = this.domSanitizer.bypassSecurityTrustResourceUrl('http://localhost:3000/');

  constructor(private router: Router, private domSanitizer: DomSanitizer) { }

  protected showFlights(): void {    
    this.iframeUrl = this.domSanitizer.bypassSecurityTrustResourceUrl('http://localhost:3000/');
  }

  protected showHotels(): void {
    this.iframeUrl = this.domSanitizer.bypassSecurityTrustResourceUrl('http://localhost:8080/');
  }

  protected showPayment(): void {
    this.router.navigate(['/payment']);
  }
}

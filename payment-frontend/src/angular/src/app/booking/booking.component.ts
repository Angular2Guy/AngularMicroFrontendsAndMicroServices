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
import { AfterViewInit, Component } from '@angular/core';
import {MatButtonModule} from '@angular/material/button';
import { Router } from '@angular/router';
import {DomSanitizer} from '@angular/platform-browser';
import {MatDialog, MatDialogModule} from '@angular/material/dialog'; 
import { SpinnerComponent } from '../spinner/spinner.component';

@Component({
    selector: 'app-booking',
    imports: [MatButtonModule,MatDialogModule],
    templateUrl: './booking.component.html',
    styleUrl: './booking.component.scss'
})
export class BookingComponent implements  AfterViewInit  {
  protected iframeUrl = this.domSanitizer.bypassSecurityTrustResourceUrl('http://localhost:3000/');

  constructor(private router: Router, private domSanitizer: DomSanitizer, private dialog: MatDialog) { }
  
  ngAfterViewInit(): void {
    this.showDialog();
  }

  protected showFlights(): void {
    this.iframeUrl = this.domSanitizer.bypassSecurityTrustResourceUrl('http://localhost:3000/');
    this.showDialog();
  }

  protected showHotels(): void {
    this.iframeUrl = this.domSanitizer.bypassSecurityTrustResourceUrl('http://localhost:8088/');
    this.showDialog();
  }

  protected showPayment(): void {
    this.router.navigate(['/payment']);    
    this.showDialog();
  }

  private showDialog(): void {
    const myDialog = this.dialog.open(SpinnerComponent, {width: '350px', height: '350px', disableClose: true, enterAnimationDuration: '500ms', exitAnimationDuration: '500ms', 
      backdropClass: 'dialog-backdrop', hasBackdrop: true});
    setTimeout(() => myDialog.close(), 2000);
  }
}

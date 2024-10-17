import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BookFlightComponent } from './book-flight.component';

describe('BookFlightComponent', () => {
  let component: BookFlightComponent;
  let fixture: ComponentFixture<BookFlightComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BookFlightComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BookFlightComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreatePerformanceModalComponent } from './create-performance-modal.component';

describe('CreatePerformanceModalComponent', () => {
  let component: CreatePerformanceModalComponent;
  let fixture: ComponentFixture<CreatePerformanceModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreatePerformanceModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreatePerformanceModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

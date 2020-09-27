import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HandleTransactionComponent } from './handle-transaction.component';

describe('HandleTransactionComponent', () => {
  let component: HandleTransactionComponent;
  let fixture: ComponentFixture<HandleTransactionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HandleTransactionComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(HandleTransactionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

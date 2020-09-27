import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TransactionTableViewComponent } from './transaction-table-view.component';

describe('TransactionTableViewComponent', () => {
  let component: TransactionTableViewComponent;
  let fixture: ComponentFixture<TransactionTableViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TransactionTableViewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TransactionTableViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

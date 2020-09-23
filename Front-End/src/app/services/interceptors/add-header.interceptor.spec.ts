import { TestBed } from '@angular/core/testing';

import { AddHeaderInterceptor } from './add-header.interceptor';

describe('AddHeaderService', () => {
  let service: AddHeaderInterceptor;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AddHeaderInterceptor);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

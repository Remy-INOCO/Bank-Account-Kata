import { TestBed } from '@angular/core/testing';

import { LogoutResolver } from './logout-resolver.service';

describe('LogoutService', () => {
  let service: LogoutResolver;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LogoutResolver);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

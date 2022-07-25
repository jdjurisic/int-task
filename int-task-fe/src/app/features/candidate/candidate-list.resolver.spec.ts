import { TestBed } from '@angular/core/testing';

import { CandidateListResolver } from './candidate-list.resolver';

describe('CandidateListResolver', () => {
  let resolver: CandidateListResolver;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    resolver = TestBed.inject(CandidateListResolver);
  });

  it('should be created', () => {
    expect(resolver).toBeTruthy();
  });
});

import { TestBed } from '@angular/core/testing';

import { SkillListResolver } from './skill-list.resolver';

describe('SkillListResolver', () => {
  let resolver: SkillListResolver;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    resolver = TestBed.inject(SkillListResolver);
  });

  it('should be created', () => {
    expect(resolver).toBeTruthy();
  });
});

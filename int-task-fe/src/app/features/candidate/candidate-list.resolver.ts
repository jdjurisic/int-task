import { Injectable } from '@angular/core';
import {
  Router, Resolve,
  RouterStateSnapshot,
  ActivatedRouteSnapshot,
  ActivatedRoute
} from '@angular/router';
import { Observable, of } from 'rxjs';
import { Candidate } from 'src/app/core/models/entities/candidate';
import { PageDto } from 'src/app/core/models/page.dto';
import { CandidateService } from 'src/app/core/services/candidate.service';

@Injectable({
  providedIn: 'root'
})
export class CandidateListResolver   implements Resolve<PageDto<Candidate>>
{
  constructor(
    private candidateService: CandidateService,
    private activeRoute: ActivatedRoute
  ) {}

  resolve(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<PageDto<Candidate>> {
    const page = Number(this.activeRoute.snapshot.queryParams['page']);
    return this.candidateService.getByPage(page ? page : 1, 5);
  }
}
import { Injectable } from '@angular/core';
import {
  Router, Resolve,
  RouterStateSnapshot,
  ActivatedRouteSnapshot,
  ActivatedRoute
} from '@angular/router';
import { Observable, of } from 'rxjs';
import { Skill } from 'src/app/core/models/entities/skill';
import { PageDto } from 'src/app/core/models/page.dto';
import { SkillService } from 'src/app/core/services/skill.service';

@Injectable({
  providedIn: 'root'
})
export class SkillListResolver   implements Resolve<PageDto<Skill>>
{
  constructor(
    private skillService: SkillService,
    private activeRoute: ActivatedRoute
  ) {}

  resolve(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<PageDto<Skill>> {
    const page = Number(this.activeRoute.snapshot.queryParams['page']);
    return this.skillService.getByPage(page ? page : 1, 5);
  }
}

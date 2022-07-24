import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Skill } from '../models/entities/skill';
import { PageDto } from '../models/page.dto';

@Injectable({
  providedIn: 'root'
})
export class SkillService {

  readonly controllerUrl = '/skill';
  constructor(private httpClient: HttpClient) {}

  getAll(): Observable<Skill[]> {
    return this.httpClient.get<Skill[]>(
      `${environment.serverUrl}${this.controllerUrl}`
    );
  }

  getByPage(
    page: number,
    size: number,
    nameFilter?: string
  ) {
    if (!nameFilter)
      return this.httpClient.get<PageDto<Skill>>(
        `${environment.serverUrl}${this.controllerUrl}/filter/?pageNo=${
          page - 1
        }&pageSize=${size}`
      );
    else
      return this.httpClient.get<PageDto<Skill>>(
        `${environment.serverUrl}${this.controllerUrl}/filter/?pageNo=${
          page - 1
        }&pageSize=${size}&nameFilter=${nameFilter}`
      );
  }

  getById(skillId: number) {
    return this.httpClient.get<Skill>(
      `${environment.serverUrl}${this.controllerUrl}/${skillId}`
    );
  }

  insertOrganization(skill: Skill) {
    return this.httpClient.post<Skill>(
      `${environment.serverUrl}${this.controllerUrl}`,
      skill
    );
  }

  updateOrganization(skill: Skill) {
    return this.httpClient.put<Skill>(
      `${environment.serverUrl}${this.controllerUrl}`,
      skill
    );
  }

  deleteById(id: number) {
    return this.httpClient.delete(
      `${environment.serverUrl}${this.controllerUrl}/${id}`
    );
  }
}

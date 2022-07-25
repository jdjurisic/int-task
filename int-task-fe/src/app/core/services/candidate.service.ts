import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Candidate } from '../models/entities/candidate';
import { PageDto } from '../models/page.dto';

@Injectable({
  providedIn: 'root'
})
export class CandidateService {


  readonly controllerUrl = '/candidate';
  constructor(private httpClient: HttpClient) {}

  getAll(): Observable<Candidate[]> {
    return this.httpClient.get<Candidate[]>(
      `${environment.serverUrl}${this.controllerUrl}`
    );
  }

  getByPage(
    page: number,
    size: number,
    nameFilter?: string
  ) {
    if (!nameFilter)
      return this.httpClient.get<PageDto<Candidate>>(
        `${environment.serverUrl}${this.controllerUrl}/filter/?pageNo=${
          page - 1
        }&pageSize=${size}`
      );
    else
      return this.httpClient.get<PageDto<Candidate>>(
        `${environment.serverUrl}${this.controllerUrl}/filter/?pageNo=${
          page - 1
        }&pageSize=${size}&nameFilter=${nameFilter}`
      );
  }

  getById(candidateId: number) {
    return this.httpClient.get<Candidate>(
      `${environment.serverUrl}${this.controllerUrl}/${candidateId}`
    );
  }

  insertCandidate(candidate: Candidate) {
    return this.httpClient.post<Candidate>(
      `${environment.serverUrl}${this.controllerUrl}`,
      candidate
    );
  }

  updateCandidate(candidate: Candidate) {
    return this.httpClient.put<Candidate>(
      `${environment.serverUrl}${this.controllerUrl}/${candidate.id}`,
      candidate
    );
  }

  deleteById(id: number) {
    return this.httpClient.delete(
      `${environment.serverUrl}${this.controllerUrl}/${id}`
    );
  }
}

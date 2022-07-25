import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { debounceTime, distinctUntilChanged, Subject } from 'rxjs';
import { Candidate } from 'src/app/core/models/entities/candidate';
import { Skill } from 'src/app/core/models/entities/skill';
import { PageDto } from 'src/app/core/models/page.dto';
import { CandidateService } from 'src/app/core/services/candidate.service';
import { ToastService } from 'src/app/core/services/toast.service';

@Component({
  selector: 'app-candidate-list',
  templateUrl: './candidate-list.component.html',
  styleUrls: ['./candidate-list.component.css']
})
export class CandidateListComponent implements OnInit {
  candidates?: Candidate[];
  currentPage = 1;
  totalItems = 10;
  pageSize = 5;
  loadingData = false;

  nameFilter = '';
  nameFilterChanged: Subject<string> = new Subject<string>();

  availablePageSize = [5, 10, 15];

  constructor(
    private candidateService: CandidateService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private toastService: ToastService
  ) {
    this.nameFilterChanged
      .pipe(debounceTime(1000), distinctUntilChanged())
      .subscribe((model) => {
        this.nameFilter = model;
        this.loadCandidates();
      });
  }

  ngOnInit(): void {
    const candidatePage: PageDto<Candidate> =
      this.activatedRoute.snapshot.data['candidatePage'];
    this.candidates = candidatePage.content;
    this.currentPage = candidatePage.number + 1;
    this.totalItems = candidatePage.totalElements;
    this.pageSize = candidatePage.size;
  }

  loadCandidates() {
    this.loadingData = true;
    this.candidateService
      .getByPage(
        this.currentPage,
        this.pageSize,
        this.nameFilter
      )
      .subscribe((candidatePage) => {
        this.candidates = candidatePage.content;
        this.totalItems = candidatePage.totalElements;
        this.pageSize = candidatePage.size;
        this.loadingData = false;
      });
  }

  onPageChange(page: number) {
    this.loadCandidates();
  }

  onPageSizeChange() {
    this.loadCandidates();
  }

  deleteCandidate(candidateId: number) {
    this.candidateService.deleteById(candidateId).subscribe({
      next: (response) => {
        console.log(response);
        this.toastService.showToast('Candidate deleted successfully', {
          className: 'bg-success text-light',
        });
        this.loadCandidates();
      },
      error: (error) => {
        this.toastService.showToast('Candidate cannot be deleted.', {
          className: 'bg-warning text-light',
        });
      },
    });
  }

  changed(text: string) {
    this.nameFilterChanged.next(text);
  }
}

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import {
  debounceTime,
  distinctUntilChanged,
  filter,
  fromEvent,
  map,
  Subject,
} from 'rxjs';
import { Skill } from 'src/app/core/models/entities/skill';
import { PageDto } from 'src/app/core/models/page.dto';
import { SkillService } from 'src/app/core/services/skill.service';
import { ToastService } from 'src/app/core/services/toast.service';

@Component({
  selector: 'app-skill-list',
  templateUrl: './skill-list.component.html',
  styleUrls: ['./skill-list.component.css'],
})
export class SkillListComponent implements OnInit {
  skills?: Skill[];
  currentPage = 1;
  totalItems = 10;
  pageSize = 5;
  loadingData = false;

  nameFilter = '';
  nameFilterChanged: Subject<string> = new Subject<string>();

  availablePageSize = [5, 10, 15];

  constructor(
    private skillService: SkillService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private toastService: ToastService
  ) {
    this.nameFilterChanged
      .pipe(debounceTime(1000), distinctUntilChanged())
      .subscribe((model) => {
        this.nameFilter = model;
        this.loadSkills();
      });
  }

  ngOnInit(): void {
    const skillPage: PageDto<Skill> =
      this.activatedRoute.snapshot.data['skillPage'];
    this.skills = skillPage.content;
    this.currentPage = skillPage.number + 1;
    this.totalItems = skillPage.totalElements;
    this.pageSize = skillPage.size;
  }

  loadSkills() {
    this.loadingData = true;
    this.skillService
      .getByPage(
        this.currentPage,
        this.pageSize,
        this.nameFilter
      )
      .subscribe((skillPage) => {
        this.skills = skillPage.content;
        this.totalItems = skillPage.totalElements;
        this.pageSize = skillPage.size;
        this.loadingData = false;
      });
  }

  onPageChange(page: number) {
    this.loadSkills();
  }

  onPageSizeChange() {
    this.loadSkills();
  }

  deleteSkill(skillId: number) {
    this.skillService.deleteById(skillId).subscribe({
      next: (response) => {
        console.log(response);
        this.toastService.showToast('Skill deleted successfully', {
          className: 'bg-success text-light',
        });
        this.loadSkills();
      },
      error: (error) => {
        console.log(error);
        this.toastService.showToast('Skill cannot be deleted.', {
          className: 'bg-warning text-light',
        });
      },
    });
  }

  changed(text: string) {
    this.nameFilterChanged.next(text);
  }
}

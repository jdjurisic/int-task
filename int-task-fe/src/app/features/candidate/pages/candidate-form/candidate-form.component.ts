import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Subject, takeUntil } from 'rxjs';
import { Candidate } from 'src/app/core/models/entities/candidate';
import { Skill } from 'src/app/core/models/entities/skill';
import { CandidateService } from 'src/app/core/services/candidate.service';
import { SkillService } from 'src/app/core/services/skill.service';
import { ToastService } from 'src/app/core/services/toast.service';

@Component({
  selector: 'app-candidate-form',
  templateUrl: './candidate-form.component.html',
  styleUrls: ['./candidate-form.component.css']
})
export class CandidateFormComponent implements OnInit {

  candidateForm?: FormGroup;
  edit = false;

  availableSkills: Skill[] = [];

  destroy$: Subject<boolean> = new Subject();

  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private candidateService: CandidateService,
    private skillService: SkillService,
    private toastService: ToastService
  ) {}

  ngOnInit(): void {
    this.prepareData();
    this.skillService.getAll().subscribe((response) => this.availableSkills = response);
  }

  prepareData() {
    this.edit = this.route.snapshot.data['edit'];
    const candId = Number(this.route.snapshot.paramMap.get('id'));

    if (this.edit && candId) {
      this.loadCandidate(candId);
    } else {
      this.buildForm();
    }
  }

  loadCandidate(candId: number) {
    this.candidateService
      .getById(candId)
      .subscribe((response) => this.buildForm(response));
  }

  buildForm(candidate?: Candidate) {
    this.candidateForm = this.formBuilder.group({
      id: [candidate ? candidate.id : null],
      fullName: [candidate ? candidate.fullName : null, [Validators.required]],
      birthdate: [candidate ? candidate.birthdate : null, [Validators.required]],
      email: [
        candidate ? candidate.email : null,
        [Validators.pattern('^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$'), Validators.required],
      ],
      contactNumber: [candidate ? candidate.contactNumber : null],
      skills:[candidate? candidate.skills: []]
    });
  }

  onSubmit() {
    this.saveCandidate()
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (response) => {
          console.log(response);
          if (this.edit)
            this.toastService.showToast('Candidate edited successfully', {
              className: 'bg-success text-light',
            });
          else
            this.toastService.showToast('Candidate saved successfully', {

              className: 'bg-success text-light',
            });
          this.router.navigate(['/candidate/list']);
        },
        error: (error) => {
          console.log('error:', error);
          if (this.edit)
            this.toastService.showToast('Candidate edit failed.', {
              className: 'bg-warning text-light',
            });
          else
            this.toastService.showToast('Candidate is not saved. Email already exists.', {
              className: 'bg-warning text-light',
            });
        },
      });
  }

  saveCandidate() {
    if (this.edit) {
      return this.candidateService.updateCandidate(
        this.candidateForm?.value
      );
    } else {
      return this.candidateService.insertCandidate(
        this.candidateForm?.value
      );
    }
  }

  hasErrors(componentName: string, errorCode?: string) {
    return (
      (this.candidateForm?.get(componentName)?.dirty ||
        this.candidateForm?.get(componentName)?.touched) &&
      ((!errorCode && this.candidateForm?.get(componentName)?.errors) ||
        (errorCode &&
          this.candidateForm?.get(componentName)?.hasError(errorCode)))
    );
  }

  compareObj(
    val1: Skill,
    val2: Skill
  ) {
    if (val1 && val2) {
      return val1.id === val2.id;
    }
    return false;
  }
}

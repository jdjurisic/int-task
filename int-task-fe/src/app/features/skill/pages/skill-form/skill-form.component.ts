import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Subject, takeUntil } from 'rxjs';
import { Skill } from 'src/app/core/models/entities/skill';
import { SkillService } from 'src/app/core/services/skill.service';
import { ToastService } from 'src/app/core/services/toast.service';

@Component({
  selector: 'app-skill-form',
  templateUrl: './skill-form.component.html',
  styleUrls: ['./skill-form.component.css']
})
export class SkillFormComponent implements OnInit {
  skillForm?: FormGroup;
  edit = false;

  destroy$: Subject<boolean> = new Subject();

  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private skillService: SkillService,
    private toastService: ToastService
  ) {}

  ngOnInit(): void {
    this.prepareData();
  }

  prepareData() {
    this.edit = this.route.snapshot.data['edit'];
    const skillId = Number(this.route.snapshot.paramMap.get('id'));

    if (this.edit && skillId) {
      this.loadSkill(skillId);
    } else {
      this.buildForm();
    }
  }

  loadSkill(skillId: number) {
    this.skillService
      .getById(skillId)
      .subscribe((response) => this.buildForm(response));
  }

  buildForm(skill?: Skill) {
    this.skillForm = this.formBuilder.group({
      id: [skill ? skill.id : null],
      name: [skill ? skill.name : null, [Validators.required, Validators.minLength(1)]],
    });
  }

  onSubmit() {
    this.saveSkill()
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (response) => {
          console.log(response);
          if (this.edit)
            this.toastService.showToast('Skill edited successfully', {
              className: 'bg-success text-light',
            });
          else
            this.toastService.showToast('Skill saved successfully', {

              className: 'bg-success text-light',
            });
          this.router.navigate(['/skill/list']);
        },
        error: (error) => {
          console.log('error:', error);
          if (this.edit)
            this.toastService.showToast('Skill edit failed.', {
              className: 'bg-warning text-light',
            });
          else
            this.toastService.showToast('Skill is not saved. Skill already exists.', {
              className: 'bg-warning text-light',
            });
        },
      });
  }

  saveSkill() {
    if (this.edit) {
      return this.skillService.updateSkill(
        this.skillForm?.value
      );
    } else {
      return this.skillService.insertSkill(
        this.skillForm?.value
      );
    }
  }

  hasErrors(componentName: string, errorCode?: string) {
    return (
      (this.skillForm?.get(componentName)?.dirty ||
        this.skillForm?.get(componentName)?.touched) &&
      ((!errorCode && this.skillForm?.get(componentName)?.errors) ||
        (errorCode &&
          this.skillForm?.get(componentName)?.hasError(errorCode)))
    );
  }
}

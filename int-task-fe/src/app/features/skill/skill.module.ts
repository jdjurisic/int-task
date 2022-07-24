import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SkillRoutingModule } from './skill-routing.module';
import { SkillListComponent } from './pages/skill-list/skill-list.component';
import { SkillFormComponent } from './pages/skill-form/skill-form.component';
import { SharedModule } from 'src/app/shared/shared.module';


@NgModule({
  declarations: [
    SkillListComponent,
    SkillFormComponent
  ],
  imports: [
    CommonModule,
    SkillRoutingModule,
    SharedModule
  ]
})
export class SkillModule { }

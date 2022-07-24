import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SkillFormComponent } from './pages/skill-form/skill-form.component';
import { SkillListComponent } from './pages/skill-list/skill-list.component';
import { SkillListResolver } from './resolvers/skill-list.resolver';

const routes: Routes = [
  {
    path: 'list',
    component: SkillListComponent,
    resolve: { skillPage: SkillListResolver },
  },
  {
    path: 'form/:id',
    component: SkillFormComponent,
    data: { edit: true },
  },
  { path: 'form', component: SkillFormComponent, data: { edit: false } },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class SkillRoutingModule {}

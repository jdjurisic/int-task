import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CandidateListResolver } from './candidate-list.resolver';
import { CandidateFormComponent } from './pages/candidate-form/candidate-form.component';
import { CandidateListComponent } from './pages/candidate-list/candidate-list.component';

const routes: Routes = [
  {
    path: 'list',
    component: CandidateListComponent,
    resolve: { candidatePage: CandidateListResolver },
  },
  {
    path: 'form/:id',
    component: CandidateFormComponent,
    data: { edit: true },
  },
  { path: 'form', component: CandidateFormComponent, data: { edit: false } },
  { path: '**', redirectTo:'list'}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CandidateRoutingModule { }

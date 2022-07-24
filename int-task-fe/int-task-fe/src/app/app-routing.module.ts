import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  // {
  //   path: 'home',
  //   loadChildren: () =>
  //     import('./features/skill/skill.module').then((m) => m.SkillModule)
  // },
  {
    path: 'skill',
    loadChildren: () =>
      import('./features/skill/skill.module').then((m) => m.SkillModule)
  },
  {
    path: 'candidate',
    loadChildren: () =>
      import('./features/candidate/candidate.module').then((m) => m.CandidateModule)
  },
  { path: '**', redirectTo: 'skill'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

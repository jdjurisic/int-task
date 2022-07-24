import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { NgbNavModule, NgbPaginationModule, NgbToastModule } from '@ng-bootstrap/ng-bootstrap';
import { RouterModule } from '@angular/router';
import { HeaderComponent } from './components/header/header.component';
import { GlobalToastComponent } from './components/global-toast/global-toast.component';



@NgModule({
  declarations: [
    HeaderComponent,
    GlobalToastComponent
  ],
  imports: [
    CommonModule,
    NgbNavModule,
    RouterModule,
    HttpClientModule,
    NgbPaginationModule,
    NgbNavModule,
    FormsModule,
    ReactiveFormsModule,
    NgbToastModule
  ],
  exports: [
    HeaderComponent,
    NgbNavModule,
    RouterModule,
    HttpClientModule,
    NgbPaginationModule,
    NgbNavModule,
    FormsModule,
    ReactiveFormsModule,
    NgbToastModule,
    GlobalToastComponent
  ],
})
export class SharedModule { }

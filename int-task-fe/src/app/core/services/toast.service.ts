import { Injectable, TemplateRef } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ToastService {
  toasts: any[] = [];

  constructor() {}

  showToast(message: string | TemplateRef<any>, options: any = {}) {
    this.toasts.push({ message: message, ...options });
  }
}

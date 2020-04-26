import { Routes } from '@angular/router';
import { AccessDeniedComponent } from '../containers/access-denied/access-denied.component';

export const ROUTES: Routes = [
  {
    path: 'welcome',
    loadChildren: () =>
      import('../containers/welcome/welcome.module').then(m => m.WelcomeModule)
  },
  {
    path: 'access-denied',
    component: AccessDeniedComponent
  }
];

import { Routes } from '@angular/router';
import { LoginComponent } from '../containers/login/login.component';
import { RegisterComponent } from '../containers/register/register.component';
import { ActivateComponent } from '../containers/activate/activate.component';
import { UserRouteAccessGuard } from '@akfe/core/guards/user-route-access.guard';
import { ProfileComponent } from '@akfe/account/containers/profile/profile.component';

export const ROUTES: Routes = [
  {
    path: '',
    children: [
      {
        path: 'login',
        component: LoginComponent
      },
      {
        path: 'register',
        component: RegisterComponent
      },
      {
        path: 'activate',
        component: ActivateComponent
      },
      {
        path: 'profile',
        canActivate: [UserRouteAccessGuard],
        component: ProfileComponent
      }
    ]
  }
];

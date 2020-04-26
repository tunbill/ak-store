import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginComponent } from './containers/login/login.component';
import { RegisterComponent } from './containers/register/register.component';
import { RouterModule } from '@angular/router';
import { ROUTES } from './constants/routes';
import {
  NzButtonModule,
  NzCheckboxModule,
  NzFormModule,
  NzInputModule
} from 'ng-zorro-antd';
import { ReactiveFormsModule } from '@angular/forms';
import { ActivateComponent } from './containers/activate/activate.component';
import { TranslocoModule } from '@ngneat/transloco';
import { NzNotificationModule } from 'ng-zorro-antd/notification';
import { ProfileComponent } from './containers/profile/profile.component';

@NgModule({
  declarations: [
    LoginComponent,
    RegisterComponent,
    ActivateComponent,
    ProfileComponent
  ],
  imports: [
    CommonModule,
    RouterModule.forChild(ROUTES),
    NzFormModule,
    ReactiveFormsModule,
    NzInputModule,
    NzCheckboxModule,
    NzButtonModule,
    TranslocoModule,
    NzNotificationModule
  ]
})
export class AccountModule {}

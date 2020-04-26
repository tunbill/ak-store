import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { ROUTES } from '@akfe/core/constants/routes';
import { CookieService } from 'ngx-cookie-service';
import { AccessDeniedComponent } from './containers/access-denied/access-denied.component';

@NgModule({
  declarations: [AccessDeniedComponent],
  imports: [CommonModule, RouterModule.forChild(ROUTES)],
  providers: [CookieService]
})
export class CoreModule {}

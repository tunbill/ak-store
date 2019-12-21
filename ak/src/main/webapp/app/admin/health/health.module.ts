import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { AkSharedModule } from 'app/shared/shared.module';

import { AkHealthCheckComponent } from './health.component';
import { AkHealthModalComponent } from './health-modal.component';

import { healthRoute } from './health.route';

@NgModule({
  imports: [AkSharedModule, RouterModule.forChild([healthRoute])],
  declarations: [AkHealthCheckComponent, AkHealthModalComponent],
  entryComponents: [AkHealthModalComponent]
})
export class HealthModule {}

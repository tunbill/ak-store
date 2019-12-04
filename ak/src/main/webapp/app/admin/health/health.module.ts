import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { AkSharedModule } from 'app/shared/shared.module';

import { TrueHealthCheckComponent } from './health.component';
import { TrueHealthModalComponent } from './health-modal.component';

import { healthRoute } from './health.route';

@NgModule({
  imports: [AkSharedModule, RouterModule.forChild([healthRoute])],
  declarations: [TrueHealthCheckComponent, TrueHealthModalComponent],
  entryComponents: [TrueHealthModalComponent]
})
export class HealthModule {}

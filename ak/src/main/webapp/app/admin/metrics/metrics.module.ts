import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { AkSharedModule } from 'app/shared/shared.module';

import { AkMetricsMonitoringComponent } from './metrics.component';

import { metricsRoute } from './metrics.route';

@NgModule({
  imports: [AkSharedModule, RouterModule.forChild([metricsRoute])],
  declarations: [AkMetricsMonitoringComponent]
})
export class MetricsModule {}

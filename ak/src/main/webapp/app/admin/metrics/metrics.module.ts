import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { AkSharedModule } from 'app/shared/shared.module';

import { TrueMetricsMonitoringComponent } from './metrics.component';

import { metricsRoute } from './metrics.route';

@NgModule({
  imports: [AkSharedModule, RouterModule.forChild([metricsRoute])],
  declarations: [TrueMetricsMonitoringComponent]
})
export class MetricsModule {}

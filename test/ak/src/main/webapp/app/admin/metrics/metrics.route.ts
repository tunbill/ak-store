import { Route } from '@angular/router';

import { AkMetricsMonitoringComponent } from './metrics.component';

export const metricsRoute: Route = {
  path: '',
  component: AkMetricsMonitoringComponent,
  data: {
    pageTitle: 'metrics.title'
  }
};

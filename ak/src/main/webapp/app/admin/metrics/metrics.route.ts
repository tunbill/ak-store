import { Route } from '@angular/router';

import { TrueMetricsMonitoringComponent } from './metrics.component';

export const metricsRoute: Route = {
  path: '',
  component: TrueMetricsMonitoringComponent,
  data: {
    pageTitle: 'metrics.title'
  }
};

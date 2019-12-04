import { Route } from '@angular/router';

import { TrueHealthCheckComponent } from './health.component';

export const healthRoute: Route = {
  path: '',
  component: TrueHealthCheckComponent,
  data: {
    pageTitle: 'health.title'
  }
};

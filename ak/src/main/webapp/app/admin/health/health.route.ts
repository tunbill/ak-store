import { Route } from '@angular/router';

import { AkHealthCheckComponent } from './health.component';

export const healthRoute: Route = {
  path: '',
  component: AkHealthCheckComponent,
  data: {
    pageTitle: 'health.title'
  }
};

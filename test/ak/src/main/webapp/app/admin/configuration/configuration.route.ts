import { Route } from '@angular/router';

import { AkConfigurationComponent } from './configuration.component';

export const configurationRoute: Route = {
  path: '',
  component: AkConfigurationComponent,
  data: {
    pageTitle: 'configuration.title'
  }
};

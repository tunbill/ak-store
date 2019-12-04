import { Route } from '@angular/router';

import { TrueConfigurationComponent } from './configuration.component';

export const configurationRoute: Route = {
  path: '',
  component: TrueConfigurationComponent,
  data: {
    pageTitle: 'configuration.title'
  }
};

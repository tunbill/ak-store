import { Route } from '@angular/router';

import { TrueDocsComponent } from './docs.component';

export const docsRoute: Route = {
  path: '',
  component: TrueDocsComponent,
  data: {
    pageTitle: 'global.menu.admin.apidocs'
  }
};

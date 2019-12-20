import { Route } from '@angular/router';

import { AkDocsComponent } from './docs.component';

export const docsRoute: Route = {
  path: '',
  component: AkDocsComponent,
  data: {
    pageTitle: 'global.menu.admin.apidocs'
  }
};

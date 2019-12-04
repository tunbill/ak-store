import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { AkSharedModule } from 'app/shared/shared.module';

import { TrueDocsComponent } from './docs.component';

import { docsRoute } from './docs.route';

@NgModule({
  imports: [AkSharedModule, RouterModule.forChild([docsRoute])],
  declarations: [TrueDocsComponent]
})
export class DocsModule {}

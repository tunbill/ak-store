import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AkSharedModule } from 'app/shared/shared.module';
import { TermsComponent } from './terms.component';
import { TermsDetailComponent } from './terms-detail.component';
import { TermsUpdateComponent } from './terms-update.component';
import { TermsDeleteDialogComponent } from './terms-delete-dialog.component';
import { termsRoute } from './terms.route';

@NgModule({
  imports: [AkSharedModule, RouterModule.forChild(termsRoute)],
  declarations: [TermsComponent, TermsDetailComponent, TermsUpdateComponent, TermsDeleteDialogComponent],
  entryComponents: [TermsDeleteDialogComponent]
})
export class AkTermsModule {}

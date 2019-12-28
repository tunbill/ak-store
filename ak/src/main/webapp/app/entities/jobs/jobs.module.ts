import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AkSharedModule } from 'app/shared/shared.module';
import { JobsComponent } from './jobs.component';
import { JobsDetailComponent } from './jobs-detail.component';
import { JobsUpdateComponent } from './jobs-update.component';
import { JobsDeleteDialogComponent } from './jobs-delete-dialog.component';
import { jobsRoute } from './jobs.route';

@NgModule({
  imports: [AkSharedModule, RouterModule.forChild(jobsRoute)],
  declarations: [JobsComponent, JobsDetailComponent, JobsUpdateComponent, JobsDeleteDialogComponent],
  entryComponents: [JobsDeleteDialogComponent]
})
export class AkJobsModule {}

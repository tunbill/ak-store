import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AkSharedModule } from 'app/shared/shared.module';
import { CustomerTypeComponent } from './customer-type.component';
import { CustomerTypeDetailComponent } from './customer-type-detail.component';
import { CustomerTypeUpdateComponent } from './customer-type-update.component';
import { CustomerTypeDeleteDialogComponent } from './customer-type-delete-dialog.component';
import { customerTypeRoute } from './customer-type.route';

@NgModule({
  imports: [AkSharedModule, RouterModule.forChild(customerTypeRoute)],
  declarations: [CustomerTypeComponent, CustomerTypeDetailComponent, CustomerTypeUpdateComponent, CustomerTypeDeleteDialogComponent],
  entryComponents: [CustomerTypeDeleteDialogComponent]
})
export class AkCustomerTypeModule {}

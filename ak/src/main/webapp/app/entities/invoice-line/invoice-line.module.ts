import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AkSharedModule } from 'app/shared/shared.module';
import { InvoiceLineComponent } from './invoice-line.component';
import { InvoiceLineDetailComponent } from './invoice-line-detail.component';
import { InvoiceLineUpdateComponent } from './invoice-line-update.component';
import { InvoiceLineDeleteDialogComponent } from './invoice-line-delete-dialog.component';
import { invoiceLineRoute } from './invoice-line.route';

@NgModule({
  imports: [AkSharedModule, RouterModule.forChild(invoiceLineRoute)],
  declarations: [InvoiceLineComponent, InvoiceLineDetailComponent, InvoiceLineUpdateComponent, InvoiceLineDeleteDialogComponent],
  entryComponents: [InvoiceLineDeleteDialogComponent]
})
export class AkInvoiceLineModule {}

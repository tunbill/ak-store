import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IInvoiceLine } from 'app/shared/model/invoice-line.model';
import { InvoiceLineService } from './invoice-line.service';

@Component({
  templateUrl: './invoice-line-delete-dialog.component.html'
})
export class InvoiceLineDeleteDialogComponent {
  invoiceLine: IInvoiceLine;

  constructor(
    protected invoiceLineService: InvoiceLineService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.invoiceLineService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'invoiceLineListModification',
        content: 'Deleted an invoiceLine'
      });
      this.activeModal.dismiss(true);
    });
  }
}

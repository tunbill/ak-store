import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICustomerType } from 'app/shared/model/customer-type.model';
import { CustomerTypeService } from './customer-type.service';

@Component({
  templateUrl: './customer-type-delete-dialog.component.html'
})
export class CustomerTypeDeleteDialogComponent {
  customerType: ICustomerType;

  constructor(
    protected customerTypeService: CustomerTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.customerTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'customerTypeListModification',
        content: 'Deleted an customerType'
      });
      this.activeModal.dismiss(true);
    });
  }
}

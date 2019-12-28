import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IItemGroup } from 'app/shared/model/item-group.model';
import { ItemGroupService } from './item-group.service';

@Component({
  templateUrl: './item-group-delete-dialog.component.html'
})
export class ItemGroupDeleteDialogComponent {
  itemGroup: IItemGroup;

  constructor(protected itemGroupService: ItemGroupService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.itemGroupService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'itemGroupListModification',
        content: 'Deleted an itemGroup'
      });
      this.activeModal.dismiss(true);
    });
  }
}

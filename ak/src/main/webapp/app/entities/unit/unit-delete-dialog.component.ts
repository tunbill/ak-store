import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUnit } from 'app/shared/model/unit.model';
import { UnitService } from './unit.service';

@Component({
  templateUrl: './unit-delete-dialog.component.html'
})
export class UnitDeleteDialogComponent {
  unit: IUnit;

  constructor(protected unitService: UnitService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.unitService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'unitListModification',
        content: 'Deleted an unit'
      });
      this.activeModal.dismiss(true);
    });
  }
}

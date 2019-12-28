import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IIndustry } from 'app/shared/model/industry.model';
import { IndustryService } from './industry.service';

@Component({
  templateUrl: './industry-delete-dialog.component.html'
})
export class IndustryDeleteDialogComponent {
  industry: IIndustry;

  constructor(protected industryService: IndustryService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.industryService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'industryListModification',
        content: 'Deleted an industry'
      });
      this.activeModal.dismiss(true);
    });
  }
}

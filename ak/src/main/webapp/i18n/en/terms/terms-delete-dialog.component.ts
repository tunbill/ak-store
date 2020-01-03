import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITerms } from 'app/shared/model/terms.model';
import { TermsService } from './terms.service';

@Component({
  templateUrl: './terms-delete-dialog.component.html'
})
export class TermsDeleteDialogComponent {
  terms: ITerms;

  constructor(protected termsService: TermsService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.termsService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'termsListModification',
        content: 'Deleted an terms'
      });
      this.activeModal.dismiss(true);
    });
  }
}

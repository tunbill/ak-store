import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IJobs } from 'app/shared/model/jobs.model';
import { JobsService } from './jobs.service';

@Component({
  templateUrl: './jobs-delete-dialog.component.html'
})
export class JobsDeleteDialogComponent {
  jobs: IJobs;

  constructor(protected jobsService: JobsService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.jobsService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'jobsListModification',
        content: 'Deleted an jobs'
      });
      this.activeModal.dismiss(true);
    });
  }
}

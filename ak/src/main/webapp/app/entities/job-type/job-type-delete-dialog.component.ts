import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IJobType } from 'app/shared/model/job-type.model';
import { JobTypeService } from './job-type.service';

@Component({
  templateUrl: './job-type-delete-dialog.component.html'
})
export class JobTypeDeleteDialogComponent {
  jobType: IJobType;

  constructor(protected jobTypeService: JobTypeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.jobTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'jobTypeListModification',
        content: 'Deleted an jobType'
      });
      this.activeModal.dismiss(true);
    });
  }
}

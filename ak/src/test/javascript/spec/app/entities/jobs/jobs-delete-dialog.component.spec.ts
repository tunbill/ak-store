import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AkTestModule } from '../../../test.module';
import { JobsDeleteDialogComponent } from 'app/entities/jobs/jobs-delete-dialog.component';
import { JobsService } from 'app/entities/jobs/jobs.service';

describe('Component Tests', () => {
  describe('Jobs Management Delete Component', () => {
    let comp: JobsDeleteDialogComponent;
    let fixture: ComponentFixture<JobsDeleteDialogComponent>;
    let service: JobsService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AkTestModule],
        declarations: [JobsDeleteDialogComponent]
      })
        .overrideTemplate(JobsDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(JobsDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(JobsService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});

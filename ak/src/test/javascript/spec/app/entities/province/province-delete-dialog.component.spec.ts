import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AkTestModule } from '../../../test.module';
import { ProvinceDeleteDialogComponent } from 'app/entities/province/province-delete-dialog.component';
import { ProvinceService } from 'app/entities/province/province.service';

describe('Component Tests', () => {
  describe('Province Management Delete Component', () => {
    let comp: ProvinceDeleteDialogComponent;
    let fixture: ComponentFixture<ProvinceDeleteDialogComponent>;
    let service: ProvinceService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AkTestModule],
        declarations: [ProvinceDeleteDialogComponent]
      })
        .overrideTemplate(ProvinceDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProvinceDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProvinceService);
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
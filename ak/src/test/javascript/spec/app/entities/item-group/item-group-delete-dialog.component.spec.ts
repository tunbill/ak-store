import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AkTestModule } from '../../../test.module';
import { ItemGroupDeleteDialogComponent } from 'app/entities/item-group/item-group-delete-dialog.component';
import { ItemGroupService } from 'app/entities/item-group/item-group.service';

describe('Component Tests', () => {
  describe('ItemGroup Management Delete Component', () => {
    let comp: ItemGroupDeleteDialogComponent;
    let fixture: ComponentFixture<ItemGroupDeleteDialogComponent>;
    let service: ItemGroupService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AkTestModule],
        declarations: [ItemGroupDeleteDialogComponent]
      })
        .overrideTemplate(ItemGroupDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ItemGroupDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ItemGroupService);
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

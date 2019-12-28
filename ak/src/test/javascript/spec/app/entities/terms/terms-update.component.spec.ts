import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { AkTestModule } from '../../../test.module';
import { TermsUpdateComponent } from 'app/entities/terms/terms-update.component';
import { TermsService } from 'app/entities/terms/terms.service';
import { Terms } from 'app/shared/model/terms.model';

describe('Component Tests', () => {
  describe('Terms Management Update Component', () => {
    let comp: TermsUpdateComponent;
    let fixture: ComponentFixture<TermsUpdateComponent>;
    let service: TermsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AkTestModule],
        declarations: [TermsUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TermsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TermsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TermsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Terms(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Terms();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});

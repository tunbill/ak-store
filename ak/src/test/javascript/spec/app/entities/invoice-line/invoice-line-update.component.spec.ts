import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { AkTestModule } from '../../../test.module';
import { InvoiceLineUpdateComponent } from 'app/entities/invoice-line/invoice-line-update.component';
import { InvoiceLineService } from 'app/entities/invoice-line/invoice-line.service';
import { InvoiceLine } from 'app/shared/model/invoice-line.model';

describe('Component Tests', () => {
  describe('InvoiceLine Management Update Component', () => {
    let comp: InvoiceLineUpdateComponent;
    let fixture: ComponentFixture<InvoiceLineUpdateComponent>;
    let service: InvoiceLineService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AkTestModule],
        declarations: [InvoiceLineUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(InvoiceLineUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(InvoiceLineUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InvoiceLineService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new InvoiceLine(123);
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
        const entity = new InvoiceLine();
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

import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { AkTestModule } from '../../../test.module';
import { JobsUpdateComponent } from 'app/entities/jobs/jobs-update.component';
import { JobsService } from 'app/entities/jobs/jobs.service';
import { Jobs } from 'app/shared/model/jobs.model';

describe('Component Tests', () => {
  describe('Jobs Management Update Component', () => {
    let comp: JobsUpdateComponent;
    let fixture: ComponentFixture<JobsUpdateComponent>;
    let service: JobsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AkTestModule],
        declarations: [JobsUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(JobsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(JobsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(JobsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Jobs(123);
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
        const entity = new Jobs();
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

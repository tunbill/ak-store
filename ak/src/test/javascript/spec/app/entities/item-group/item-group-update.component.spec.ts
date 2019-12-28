import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { AkTestModule } from '../../../test.module';
import { ItemGroupUpdateComponent } from 'app/entities/item-group/item-group-update.component';
import { ItemGroupService } from 'app/entities/item-group/item-group.service';
import { ItemGroup } from 'app/shared/model/item-group.model';

describe('Component Tests', () => {
  describe('ItemGroup Management Update Component', () => {
    let comp: ItemGroupUpdateComponent;
    let fixture: ComponentFixture<ItemGroupUpdateComponent>;
    let service: ItemGroupService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AkTestModule],
        declarations: [ItemGroupUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ItemGroupUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ItemGroupUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ItemGroupService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ItemGroup(123);
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
        const entity = new ItemGroup();
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

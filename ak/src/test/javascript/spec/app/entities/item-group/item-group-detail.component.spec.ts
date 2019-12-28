import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AkTestModule } from '../../../test.module';
import { ItemGroupDetailComponent } from 'app/entities/item-group/item-group-detail.component';
import { ItemGroup } from 'app/shared/model/item-group.model';

describe('Component Tests', () => {
  describe('ItemGroup Management Detail Component', () => {
    let comp: ItemGroupDetailComponent;
    let fixture: ComponentFixture<ItemGroupDetailComponent>;
    const route = ({ data: of({ itemGroup: new ItemGroup(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AkTestModule],
        declarations: [ItemGroupDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ItemGroupDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ItemGroupDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.itemGroup).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

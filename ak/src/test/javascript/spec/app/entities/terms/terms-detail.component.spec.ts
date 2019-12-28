import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AkTestModule } from '../../../test.module';
import { TermsDetailComponent } from 'app/entities/terms/terms-detail.component';
import { Terms } from 'app/shared/model/terms.model';

describe('Component Tests', () => {
  describe('Terms Management Detail Component', () => {
    let comp: TermsDetailComponent;
    let fixture: ComponentFixture<TermsDetailComponent>;
    const route = ({ data: of({ terms: new Terms(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AkTestModule],
        declarations: [TermsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TermsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TermsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.terms).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

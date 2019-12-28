import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AkTestModule } from '../../../test.module';
import { IndustryComponent } from 'app/entities/industry/industry.component';
import { IndustryService } from 'app/entities/industry/industry.service';
import { Industry } from 'app/shared/model/industry.model';

describe('Component Tests', () => {
  describe('Industry Management Component', () => {
    let comp: IndustryComponent;
    let fixture: ComponentFixture<IndustryComponent>;
    let service: IndustryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AkTestModule],
        declarations: [IndustryComponent],
        providers: []
      })
        .overrideTemplate(IndustryComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(IndustryComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(IndustryService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Industry(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.industries[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});

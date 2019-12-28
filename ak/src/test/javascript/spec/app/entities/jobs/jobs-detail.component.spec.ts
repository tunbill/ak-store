import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AkTestModule } from '../../../test.module';
import { JobsDetailComponent } from 'app/entities/jobs/jobs-detail.component';
import { Jobs } from 'app/shared/model/jobs.model';

describe('Component Tests', () => {
  describe('Jobs Management Detail Component', () => {
    let comp: JobsDetailComponent;
    let fixture: ComponentFixture<JobsDetailComponent>;
    const route = ({ data: of({ jobs: new Jobs(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AkTestModule],
        declarations: [JobsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(JobsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(JobsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.jobs).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

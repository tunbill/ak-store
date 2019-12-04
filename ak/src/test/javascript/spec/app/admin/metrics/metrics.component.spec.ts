import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { of } from 'rxjs';

import { AkTestModule } from '../../../test.module';
import { TrueMetricsMonitoringComponent } from 'app/admin/metrics/metrics.component';
import { TrueMetricsService } from 'app/admin/metrics/metrics.service';

describe('Component Tests', () => {
  describe('TrueMetricsMonitoringComponent', () => {
    let comp: TrueMetricsMonitoringComponent;
    let fixture: ComponentFixture<TrueMetricsMonitoringComponent>;
    let service: TrueMetricsService;

    beforeEach(async(() => {
      TestBed.configureTestingModule({
        imports: [AkTestModule],
        declarations: [TrueMetricsMonitoringComponent]
      })
        .overrideTemplate(TrueMetricsMonitoringComponent, '')
        .compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(TrueMetricsMonitoringComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TrueMetricsService);
    });

    describe('refresh', () => {
      it('should call refresh on init', () => {
        // GIVEN
        const response = {
          timers: {
            service: 'test',
            unrelatedKey: 'test'
          },
          gauges: {
            'jcache.statistics': {
              value: 2
            },
            unrelatedKey: 'test'
          }
        };
        spyOn(service, 'getMetrics').and.returnValue(of(response));

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(service.getMetrics).toHaveBeenCalled();
      });
    });
  });
});

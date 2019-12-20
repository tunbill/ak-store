import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { of } from 'rxjs';

import { AkTestModule } from '../../../test.module';
import { AkMetricsMonitoringComponent } from 'app/admin/metrics/metrics.component';
import { AkMetricsService } from 'app/admin/metrics/metrics.service';

describe('Component Tests', () => {
  describe('AkMetricsMonitoringComponent', () => {
    let comp: AkMetricsMonitoringComponent;
    let fixture: ComponentFixture<AkMetricsMonitoringComponent>;
    let service: AkMetricsService;

    beforeEach(async(() => {
      TestBed.configureTestingModule({
        imports: [AkTestModule],
        declarations: [AkMetricsMonitoringComponent]
      })
        .overrideTemplate(AkMetricsMonitoringComponent, '')
        .compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(AkMetricsMonitoringComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AkMetricsService);
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

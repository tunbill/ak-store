import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import { InvoiceLineService } from 'app/entities/invoice-line/invoice-line.service';
import { IInvoiceLine, InvoiceLine } from 'app/shared/model/invoice-line.model';
import { ProcessStatus } from 'app/shared/model/enumerations/process-status.model';

describe('Service Tests', () => {
  describe('InvoiceLine Service', () => {
    let injector: TestBed;
    let service: InvoiceLineService;
    let httpMock: HttpTestingController;
    let elemDefault: IInvoiceLine;
    let expectedResult;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(InvoiceLineService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new InvoiceLine(0, 0, 'AAAAAAA', 'AAAAAAA', 0, 0, 0, 0, 'AAAAAAA', ProcessStatus.OPEN);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a InvoiceLine', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new InvoiceLine(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a InvoiceLine', () => {
        const returnedFromService = Object.assign(
          {
            displayOrder: 1,
            itemName: 'BBBBBB',
            unitName: 'BBBBBB',
            quantity: 1,
            rate: 1,
            amount: 1,
            discountPct: 1,
            accountNumber: 'BBBBBB',
            status: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of InvoiceLine', () => {
        const returnedFromService = Object.assign(
          {
            displayOrder: 1,
            itemName: 'BBBBBB',
            unitName: 'BBBBBB',
            quantity: 1,
            rate: 1,
            amount: 1,
            discountPct: 1,
            accountNumber: 'BBBBBB',
            status: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a InvoiceLine', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});

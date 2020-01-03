import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { CustomerService } from 'app/entities/customer/customer.service';
import { ICustomer, Customer } from 'app/shared/model/customer.model';

describe('Service Tests', () => {
  describe('Customer Service', () => {
    let injector: TestBed;
    let service: CustomerService;
    let httpMock: HttpTestingController;
    let elemDefault: ICustomer;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(CustomerService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Customer(
        0,
        0,
        false,
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        0,
        0,
        currentDate,
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        false,
        currentDate,
        currentDate,
        0,
        0
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            openBalanceDate: currentDate.format(DATE_TIME_FORMAT),
            timeCreated: currentDate.format(DATE_TIME_FORMAT),
            timeModified: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a Customer', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            openBalanceDate: currentDate.format(DATE_TIME_FORMAT),
            timeCreated: currentDate.format(DATE_TIME_FORMAT),
            timeModified: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            openBalanceDate: currentDate,
            timeCreated: currentDate,
            timeModified: currentDate
          },
          returnedFromService
        );
        service
          .create(new Customer(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Customer', () => {
        const returnedFromService = Object.assign(
          {
            companyId: 1,
            isVendor: true,
            vendorId: 1,
            code: 'BBBBBB',
            companyName: 'BBBBBB',
            address: 'BBBBBB',
            phone: 'BBBBBB',
            mobile: 'BBBBBB',
            fax: 'BBBBBB',
            email: 'BBBBBB',
            website: 'BBBBBB',
            taxCode: 'BBBBBB',
            accountNumber: 'BBBBBB',
            bankAccount: 'BBBBBB',
            bankName: 'BBBBBB',
            balance: 1,
            totalBalance: 1,
            openBalance: 1,
            openBalanceDate: currentDate.format(DATE_TIME_FORMAT),
            creditLimit: 1,
            notes: 'BBBBBB',
            contactName: 'BBBBBB',
            contactMobile: 'BBBBBB',
            contactEmail: 'BBBBBB',
            isActive: true,
            timeCreated: currentDate.format(DATE_TIME_FORMAT),
            timeModified: currentDate.format(DATE_TIME_FORMAT),
            userIdCreated: 1,
            userIdModified: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            openBalanceDate: currentDate,
            timeCreated: currentDate,
            timeModified: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of Customer', () => {
        const returnedFromService = Object.assign(
          {
            companyId: 1,
            isVendor: true,
            vendorId: 1,
            code: 'BBBBBB',
            companyName: 'BBBBBB',
            address: 'BBBBBB',
            phone: 'BBBBBB',
            mobile: 'BBBBBB',
            fax: 'BBBBBB',
            email: 'BBBBBB',
            website: 'BBBBBB',
            taxCode: 'BBBBBB',
            accountNumber: 'BBBBBB',
            bankAccount: 'BBBBBB',
            bankName: 'BBBBBB',
            balance: 1,
            totalBalance: 1,
            openBalance: 1,
            openBalanceDate: currentDate.format(DATE_TIME_FORMAT),
            creditLimit: 1,
            notes: 'BBBBBB',
            contactName: 'BBBBBB',
            contactMobile: 'BBBBBB',
            contactEmail: 'BBBBBB',
            isActive: true,
            timeCreated: currentDate.format(DATE_TIME_FORMAT),
            timeModified: currentDate.format(DATE_TIME_FORMAT),
            userIdCreated: 1,
            userIdModified: 1
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            openBalanceDate: currentDate,
            timeCreated: currentDate,
            timeModified: currentDate
          },
          returnedFromService
        );
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

      it('should delete a Customer', () => {
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

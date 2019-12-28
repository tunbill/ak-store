import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { EmployeeService } from 'app/entities/employee/employee.service';
import { IEmployee, Employee } from 'app/shared/model/employee.model';

describe('Service Tests', () => {
  describe('Employee Service', () => {
    let injector: TestBed;
    let service: EmployeeService;
    let httpMock: HttpTestingController;
    let elemDefault: IEmployee;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(EmployeeService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Employee(
        0,
        'AAAAAAA',
        'AAAAAAA',
        0,
        currentDate,
        'AAAAAAA',
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        0,
        0,
        0,
        'AAAAAAA',
        'AAAAAAA',
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
            birthday: currentDate.format(DATE_FORMAT),
            identityDate: currentDate.format(DATE_FORMAT),
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

      it('should create a Employee', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            birthday: currentDate.format(DATE_FORMAT),
            identityDate: currentDate.format(DATE_FORMAT),
            timeCreated: currentDate.format(DATE_TIME_FORMAT),
            timeModified: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            birthday: currentDate,
            identityDate: currentDate,
            timeCreated: currentDate,
            timeModified: currentDate
          },
          returnedFromService
        );
        service
          .create(new Employee(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Employee', () => {
        const returnedFromService = Object.assign(
          {
            code: 'BBBBBB',
            fullName: 'BBBBBB',
            sex: 1,
            birthday: currentDate.format(DATE_FORMAT),
            identityCard: 'BBBBBB',
            identityDate: currentDate.format(DATE_FORMAT),
            identityIssue: 'BBBBBB',
            position: 'BBBBBB',
            taxCode: 'BBBBBB',
            salary: 1,
            salaryRate: 1,
            salarySecurity: 1,
            numOfDepends: 1,
            phone: 'BBBBBB',
            mobile: 'BBBBBB',
            email: 'BBBBBB',
            bankAccount: 'BBBBBB',
            bankName: 'BBBBBB',
            nodes: 'BBBBBB',
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
            birthday: currentDate,
            identityDate: currentDate,
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

      it('should return a list of Employee', () => {
        const returnedFromService = Object.assign(
          {
            code: 'BBBBBB',
            fullName: 'BBBBBB',
            sex: 1,
            birthday: currentDate.format(DATE_FORMAT),
            identityCard: 'BBBBBB',
            identityDate: currentDate.format(DATE_FORMAT),
            identityIssue: 'BBBBBB',
            position: 'BBBBBB',
            taxCode: 'BBBBBB',
            salary: 1,
            salaryRate: 1,
            salarySecurity: 1,
            numOfDepends: 1,
            phone: 'BBBBBB',
            mobile: 'BBBBBB',
            email: 'BBBBBB',
            bankAccount: 'BBBBBB',
            bankName: 'BBBBBB',
            nodes: 'BBBBBB',
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
            birthday: currentDate,
            identityDate: currentDate,
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

      it('should delete a Employee', () => {
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

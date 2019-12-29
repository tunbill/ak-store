import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ItemService } from 'app/entities/item/item.service';
import { IItem, Item } from 'app/shared/model/item.model';
import { ItemType } from 'app/shared/model/enumerations/item-type.model';
import { VATTax } from 'app/shared/model/enumerations/vat-tax.model';
import { PriceMethod } from 'app/shared/model/enumerations/price-method.model';

describe('Service Tests', () => {
  describe('Item Service', () => {
    let injector: TestBed;
    let service: ItemService;
    let httpMock: HttpTestingController;
    let elemDefault: IItem;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(ItemService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Item(
        0,
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        ItemType.PRODUCT,
        0,
        VATTax.VAT5,
        0,
        0,
        PriceMethod.AVERAGE,
        'AAAAAAA',
        false,
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
        0,
        'AAAAAAA',
        'AAAAAAA',
        'image/png',
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

      it('should create a Item', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            timeCreated: currentDate.format(DATE_TIME_FORMAT),
            timeModified: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            timeCreated: currentDate,
            timeModified: currentDate
          },
          returnedFromService
        );
        service
          .create(new Item(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Item', () => {
        const returnedFromService = Object.assign(
          {
            companyId: 1,
            code: 'BBBBBB',
            name: 'BBBBBB',
            description: 'BBBBBB',
            type: 'BBBBBB',
            skuNum: 1,
            vatTax: 'BBBBBB',
            importTax: 1,
            exportTax: 1,
            inventoryPriceMethod: 'BBBBBB',
            accountItem: 'BBBBBB',
            isAllowModified: true,
            accountCost: 'BBBBBB',
            accountRevenue: 'BBBBBB',
            accountInternalRevenue: 'BBBBBB',
            accountSaleReturn: 'BBBBBB',
            accountSalePrice: 'BBBBBB',
            accountAgency: 'BBBBBB',
            accountRawProduct: 'BBBBBB',
            accountCostDifference: 'BBBBBB',
            accountDiscount: 'BBBBBB',
            saleDesc: 'BBBBBB',
            purchaseDesc: 'BBBBBB',
            weight: 1,
            lenght: 1,
            wide: 1,
            height: 1,
            color: 'BBBBBB',
            specification: 'BBBBBB',
            itemImage: 'BBBBBB',
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

      it('should return a list of Item', () => {
        const returnedFromService = Object.assign(
          {
            companyId: 1,
            code: 'BBBBBB',
            name: 'BBBBBB',
            description: 'BBBBBB',
            type: 'BBBBBB',
            skuNum: 1,
            vatTax: 'BBBBBB',
            importTax: 1,
            exportTax: 1,
            inventoryPriceMethod: 'BBBBBB',
            accountItem: 'BBBBBB',
            isAllowModified: true,
            accountCost: 'BBBBBB',
            accountRevenue: 'BBBBBB',
            accountInternalRevenue: 'BBBBBB',
            accountSaleReturn: 'BBBBBB',
            accountSalePrice: 'BBBBBB',
            accountAgency: 'BBBBBB',
            accountRawProduct: 'BBBBBB',
            accountCostDifference: 'BBBBBB',
            accountDiscount: 'BBBBBB',
            saleDesc: 'BBBBBB',
            purchaseDesc: 'BBBBBB',
            weight: 1,
            lenght: 1,
            wide: 1,
            height: 1,
            color: 'BBBBBB',
            specification: 'BBBBBB',
            itemImage: 'BBBBBB',
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

      it('should delete a Item', () => {
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

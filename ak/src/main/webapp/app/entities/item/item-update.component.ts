import { Component, OnInit, ElementRef } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IItem, Item } from 'app/shared/model/item.model';
import { ItemService } from './item.service';
import { IUnit } from 'app/shared/model/unit.model';
import { UnitService } from 'app/entities/unit/unit.service';
import { IItemGroup } from 'app/shared/model/item-group.model';
import { ItemGroupService } from 'app/entities/item-group/item-group.service';
import { IStore } from 'app/shared/model/store.model';
import { StoreService } from 'app/entities/store/store.service';
import { ICompany } from 'app/shared/model/company.model';
import { CompanyService } from 'app/entities/company/company.service';

@Component({
  selector: 'ak-item-update',
  templateUrl: './item-update.component.html'
})
export class ItemUpdateComponent implements OnInit {
  isSaving: boolean;

  units: IUnit[];

  itemgroups: IItemGroup[];

  stores: IStore[];

  companies: ICompany[];

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.maxLength(20)]],
    name: [null, [Validators.required, Validators.maxLength(150)]],
    description: [null, [Validators.maxLength(200)]],
    type: [null, [Validators.required]],
    skuNum: [],
    vatTax: [],
    importTax: [],
    exportTax: [],
    inventoryPriceMethod: [],
    accountItem: [null, [Validators.maxLength(10)]],
    isAllowModified: [],
    accountCost: [null, [Validators.maxLength(10)]],
    accountRevenue: [null, [Validators.maxLength(10)]],
    accountInternalRevenue: [null, [Validators.maxLength(10)]],
    accountSaleReturn: [null, [Validators.maxLength(10)]],
    accountSalePrice: [null, [Validators.maxLength(10)]],
    accountAgency: [null, [Validators.maxLength(10)]],
    accountRawProduct: [null, [Validators.maxLength(10)]],
    accountCostDifference: [null, [Validators.maxLength(10)]],
    accountDiscount: [null, [Validators.maxLength(10)]],
    saleDesc: [null, [Validators.maxLength(100)]],
    purchaseDesc: [null, [Validators.maxLength(100)]],
    weight: [],
    lenght: [],
    wide: [],
    height: [],
    color: [],
    specification: [],
    itemImage: [],
    itemImageContentType: [],
    isActive: [],
    timeCreated: [],
    timeModified: [],
    userIdCreated: [],
    userIdModified: [],
    unit: [],
    itemGroup: [],
    store: [],
    company: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected itemService: ItemService,
    protected unitService: UnitService,
    protected itemGroupService: ItemGroupService,
    protected storeService: StoreService,
    protected companyService: CompanyService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ item }) => {
      this.updateForm(item);
    });
    this.unitService
      .query()
      .subscribe((res: HttpResponse<IUnit[]>) => (this.units = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.itemGroupService
      .query()
      .subscribe((res: HttpResponse<IItemGroup[]>) => (this.itemgroups = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.storeService
      .query()
      .subscribe((res: HttpResponse<IStore[]>) => (this.stores = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.companyService
      .query()
      .subscribe((res: HttpResponse<ICompany[]>) => (this.companies = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(item: IItem) {
    this.editForm.patchValue({
      id: item.id,
      code: item.code,
      name: item.name,
      description: item.description,
      type: item.type,
      skuNum: item.skuNum,
      vatTax: item.vatTax,
      importTax: item.importTax,
      exportTax: item.exportTax,
      inventoryPriceMethod: item.inventoryPriceMethod,
      accountItem: item.accountItem,
      isAllowModified: item.isAllowModified,
      accountCost: item.accountCost,
      accountRevenue: item.accountRevenue,
      accountInternalRevenue: item.accountInternalRevenue,
      accountSaleReturn: item.accountSaleReturn,
      accountSalePrice: item.accountSalePrice,
      accountAgency: item.accountAgency,
      accountRawProduct: item.accountRawProduct,
      accountCostDifference: item.accountCostDifference,
      accountDiscount: item.accountDiscount,
      saleDesc: item.saleDesc,
      purchaseDesc: item.purchaseDesc,
      weight: item.weight,
      lenght: item.lenght,
      wide: item.wide,
      height: item.height,
      color: item.color,
      specification: item.specification,
      itemImage: item.itemImage,
      itemImageContentType: item.itemImageContentType,
      isActive: item.isActive,
      timeCreated: item.timeCreated != null ? item.timeCreated.format(DATE_TIME_FORMAT) : null,
      timeModified: item.timeModified != null ? item.timeModified.format(DATE_TIME_FORMAT) : null,
      userIdCreated: item.userIdCreated,
      userIdModified: item.userIdModified,
      unit: item.unit,
      itemGroup: item.itemGroup,
      store: item.store,
      company: item.company
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  setFileData(event, field: string, isImage) {
    return new Promise((resolve, reject) => {
      if (event && event.target && event.target.files && event.target.files[0]) {
        const file: File = event.target.files[0];
        if (isImage && !file.type.startsWith('image/')) {
          reject(`File was expected to be an image but was found to be ${file.type}`);
        } else {
          const filedContentType: string = field + 'ContentType';
          this.dataUtils.toBase64(file, base64Data => {
            this.editForm.patchValue({
              [field]: base64Data,
              [filedContentType]: file.type
            });
          });
        }
      } else {
        reject(`Base64 data was not set as file could not be extracted from passed parameter: ${event}`);
      }
    }).then(
      // eslint-disable-next-line no-console
      () => console.log('blob added'), // success
      this.onError
    );
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string) {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const item = this.createFromForm();
    if (item.id !== undefined) {
      this.subscribeToSaveResponse(this.itemService.update(item));
    } else {
      this.subscribeToSaveResponse(this.itemService.create(item));
    }
  }

  private createFromForm(): IItem {
    return {
      ...new Item(),
      id: this.editForm.get(['id']).value,
      code: this.editForm.get(['code']).value,
      name: this.editForm.get(['name']).value,
      description: this.editForm.get(['description']).value,
      type: this.editForm.get(['type']).value,
      skuNum: this.editForm.get(['skuNum']).value,
      vatTax: this.editForm.get(['vatTax']).value,
      importTax: this.editForm.get(['importTax']).value,
      exportTax: this.editForm.get(['exportTax']).value,
      inventoryPriceMethod: this.editForm.get(['inventoryPriceMethod']).value,
      accountItem: this.editForm.get(['accountItem']).value,
      isAllowModified: this.editForm.get(['isAllowModified']).value,
      accountCost: this.editForm.get(['accountCost']).value,
      accountRevenue: this.editForm.get(['accountRevenue']).value,
      accountInternalRevenue: this.editForm.get(['accountInternalRevenue']).value,
      accountSaleReturn: this.editForm.get(['accountSaleReturn']).value,
      accountSalePrice: this.editForm.get(['accountSalePrice']).value,
      accountAgency: this.editForm.get(['accountAgency']).value,
      accountRawProduct: this.editForm.get(['accountRawProduct']).value,
      accountCostDifference: this.editForm.get(['accountCostDifference']).value,
      accountDiscount: this.editForm.get(['accountDiscount']).value,
      saleDesc: this.editForm.get(['saleDesc']).value,
      purchaseDesc: this.editForm.get(['purchaseDesc']).value,
      weight: this.editForm.get(['weight']).value,
      lenght: this.editForm.get(['lenght']).value,
      wide: this.editForm.get(['wide']).value,
      height: this.editForm.get(['height']).value,
      color: this.editForm.get(['color']).value,
      specification: this.editForm.get(['specification']).value,
      itemImageContentType: this.editForm.get(['itemImageContentType']).value,
      itemImage: this.editForm.get(['itemImage']).value,
      isActive: this.editForm.get(['isActive']).value,
      timeCreated:
        this.editForm.get(['timeCreated']).value != null ? moment(this.editForm.get(['timeCreated']).value, DATE_TIME_FORMAT) : undefined,
      timeModified:
        this.editForm.get(['timeModified']).value != null ? moment(this.editForm.get(['timeModified']).value, DATE_TIME_FORMAT) : undefined,
      userIdCreated: this.editForm.get(['userIdCreated']).value,
      userIdModified: this.editForm.get(['userIdModified']).value,
      unit: this.editForm.get(['unit']).value,
      itemGroup: this.editForm.get(['itemGroup']).value,
      store: this.editForm.get(['store']).value,
      company: this.editForm.get(['company']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IItem>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackUnitById(index: number, item: IUnit) {
    return item.id;
  }

  trackItemGroupById(index: number, item: IItemGroup) {
    return item.id;
  }

  trackStoreById(index: number, item: IStore) {
    return item.id;
  }

  trackCompanyById(index: number, item: ICompany) {
    return item.id;
  }
}

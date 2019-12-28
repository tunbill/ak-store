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
import { ICompany, Company } from 'app/shared/model/company.model';
import { CompanyService } from './company.service';
import { IIndustry } from 'app/shared/model/industry.model';
import { IndustryService } from 'app/entities/industry/industry.service';
import { IProvince } from 'app/shared/model/province.model';
import { ProvinceService } from 'app/entities/province/province.service';

@Component({
  selector: 'ak-company-update',
  templateUrl: './company-update.component.html'
})
export class CompanyUpdateComponent implements OnInit {
  isSaving: boolean;

  industries: IIndustry[];

  provinces: IProvince[];
  startDateDp: any;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    address: [],
    logo: [],
    logoContentType: [],
    email: [null, [Validators.maxLength(50), Validators.pattern('^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$')]],
    startDate: [],
    numOfUsers: [null, [Validators.min(1)]],
    type: [null, [Validators.maxLength(10)]],
    isActive: [],
    timeCreated: [],
    timeModified: [],
    userId: [],
    industry: [],
    province: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected companyService: CompanyService,
    protected industryService: IndustryService,
    protected provinceService: ProvinceService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ company }) => {
      this.updateForm(company);
    });
    this.industryService
      .query()
      .subscribe((res: HttpResponse<IIndustry[]>) => (this.industries = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.provinceService
      .query()
      .subscribe((res: HttpResponse<IProvince[]>) => (this.provinces = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(company: ICompany) {
    this.editForm.patchValue({
      id: company.id,
      name: company.name,
      address: company.address,
      logo: company.logo,
      logoContentType: company.logoContentType,
      email: company.email,
      startDate: company.startDate,
      numOfUsers: company.numOfUsers,
      type: company.type,
      isActive: company.isActive,
      timeCreated: company.timeCreated != null ? company.timeCreated.format(DATE_TIME_FORMAT) : null,
      timeModified: company.timeModified != null ? company.timeModified.format(DATE_TIME_FORMAT) : null,
      userId: company.userId,
      industry: company.industry,
      province: company.province
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
    const company = this.createFromForm();
    if (company.id !== undefined) {
      this.subscribeToSaveResponse(this.companyService.update(company));
    } else {
      this.subscribeToSaveResponse(this.companyService.create(company));
    }
  }

  private createFromForm(): ICompany {
    return {
      ...new Company(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      address: this.editForm.get(['address']).value,
      logoContentType: this.editForm.get(['logoContentType']).value,
      logo: this.editForm.get(['logo']).value,
      email: this.editForm.get(['email']).value,
      startDate: this.editForm.get(['startDate']).value,
      numOfUsers: this.editForm.get(['numOfUsers']).value,
      type: this.editForm.get(['type']).value,
      isActive: this.editForm.get(['isActive']).value,
      timeCreated:
        this.editForm.get(['timeCreated']).value != null ? moment(this.editForm.get(['timeCreated']).value, DATE_TIME_FORMAT) : undefined,
      timeModified:
        this.editForm.get(['timeModified']).value != null ? moment(this.editForm.get(['timeModified']).value, DATE_TIME_FORMAT) : undefined,
      userId: this.editForm.get(['userId']).value,
      industry: this.editForm.get(['industry']).value,
      province: this.editForm.get(['province']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICompany>>) {
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

  trackIndustryById(index: number, item: IIndustry) {
    return item.id;
  }

  trackProvinceById(index: number, item: IProvince) {
    return item.id;
  }
}

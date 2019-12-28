import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IJobType, JobType } from 'app/shared/model/job-type.model';
import { JobTypeService } from './job-type.service';
import { ICompany } from 'app/shared/model/company.model';
import { CompanyService } from 'app/entities/company/company.service';

@Component({
  selector: 'ak-job-type-update',
  templateUrl: './job-type-update.component.html'
})
export class JobTypeUpdateComponent implements OnInit {
  isSaving: boolean;

  companies: ICompany[];

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.maxLength(20)]],
    name: [null, [Validators.required, Validators.maxLength(100)]],
    company: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected jobTypeService: JobTypeService,
    protected companyService: CompanyService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ jobType }) => {
      this.updateForm(jobType);
    });
    this.companyService
      .query()
      .subscribe((res: HttpResponse<ICompany[]>) => (this.companies = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(jobType: IJobType) {
    this.editForm.patchValue({
      id: jobType.id,
      code: jobType.code,
      name: jobType.name,
      company: jobType.company
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const jobType = this.createFromForm();
    if (jobType.id !== undefined) {
      this.subscribeToSaveResponse(this.jobTypeService.update(jobType));
    } else {
      this.subscribeToSaveResponse(this.jobTypeService.create(jobType));
    }
  }

  private createFromForm(): IJobType {
    return {
      ...new JobType(),
      id: this.editForm.get(['id']).value,
      code: this.editForm.get(['code']).value,
      name: this.editForm.get(['name']).value,
      company: this.editForm.get(['company']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IJobType>>) {
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

  trackCompanyById(index: number, item: ICompany) {
    return item.id;
  }
}

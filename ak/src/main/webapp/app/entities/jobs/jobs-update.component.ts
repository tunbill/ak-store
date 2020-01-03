import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IJobs, Jobs } from 'app/shared/model/jobs.model';
import { JobsService } from './jobs.service';
import { IJobType } from 'app/shared/model/job-type.model';
import { JobTypeService } from 'app/entities/job-type/job-type.service';

@Component({
  selector: 'ak-jobs-update',
  templateUrl: './jobs-update.component.html'
})
export class JobsUpdateComponent implements OnInit {
  isSaving: boolean;

  jobtypes: IJobType[];
  startDateDp: any;
  endDateDp: any;

  editForm = this.fb.group({
    id: [],
    companyId: [],
    code: [null, [Validators.maxLength(20)]],
    name: [null, [Validators.maxLength(100)]],
    status: [],
    startDate: [],
    endDate: [],
    estimate: [],
    investor: [null, [Validators.maxLength(100)]],
    address: [null, [Validators.maxLength(300)]],
    notes: [null, [Validators.maxLength(200)]],
    isActive: [],
    jobType: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected jobsService: JobsService,
    protected jobTypeService: JobTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ jobs }) => {
      this.updateForm(jobs);
    });
    this.jobTypeService
      .query()
      .subscribe((res: HttpResponse<IJobType[]>) => (this.jobtypes = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(jobs: IJobs) {
    this.editForm.patchValue({
      id: jobs.id,
      companyId: jobs.companyId,
      code: jobs.code,
      name: jobs.name,
      status: jobs.status,
      startDate: jobs.startDate,
      endDate: jobs.endDate,
      estimate: jobs.estimate,
      investor: jobs.investor,
      address: jobs.address,
      notes: jobs.notes,
      isActive: jobs.isActive,
      jobType: jobs.jobType
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const jobs = this.createFromForm();
    if (jobs.id !== undefined) {
      this.subscribeToSaveResponse(this.jobsService.update(jobs));
    } else {
      this.subscribeToSaveResponse(this.jobsService.create(jobs));
    }
  }

  private createFromForm(): IJobs {
    return {
      ...new Jobs(),
      id: this.editForm.get(['id']).value,
      companyId: this.editForm.get(['companyId']).value,
      code: this.editForm.get(['code']).value,
      name: this.editForm.get(['name']).value,
      status: this.editForm.get(['status']).value,
      startDate: this.editForm.get(['startDate']).value,
      endDate: this.editForm.get(['endDate']).value,
      estimate: this.editForm.get(['estimate']).value,
      investor: this.editForm.get(['investor']).value,
      address: this.editForm.get(['address']).value,
      notes: this.editForm.get(['notes']).value,
      isActive: this.editForm.get(['isActive']).value,
      jobType: this.editForm.get(['jobType']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IJobs>>) {
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

  trackJobTypeById(index: number, item: IJobType) {
    return item.id;
  }
}

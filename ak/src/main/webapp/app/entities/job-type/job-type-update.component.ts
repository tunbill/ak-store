import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IJobType, JobType } from 'app/shared/model/job-type.model';
import { JobTypeService } from './job-type.service';

@Component({
  selector: 'ak-job-type-update',
  templateUrl: './job-type-update.component.html'
})
export class JobTypeUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    companyId: [],
    code: [null, [Validators.maxLength(20)]],
    name: [null, [Validators.required, Validators.maxLength(50)]],
    description: [null, [Validators.maxLength(200)]],
    isActive: []
  });

  constructor(protected jobTypeService: JobTypeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ jobType }) => {
      this.updateForm(jobType);
    });
  }

  updateForm(jobType: IJobType) {
    this.editForm.patchValue({
      id: jobType.id,
      companyId: jobType.companyId,
      code: jobType.code,
      name: jobType.name,
      description: jobType.description,
      isActive: jobType.isActive
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
      companyId: this.editForm.get(['companyId']).value,
      code: this.editForm.get(['code']).value,
      name: this.editForm.get(['name']).value,
      description: this.editForm.get(['description']).value,
      isActive: this.editForm.get(['isActive']).value
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
}

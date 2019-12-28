import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IEmployee, Employee } from 'app/shared/model/employee.model';
import { EmployeeService } from './employee.service';
import { IDepartment } from 'app/shared/model/department.model';
import { DepartmentService } from 'app/entities/department/department.service';
import { ICompany } from 'app/shared/model/company.model';
import { CompanyService } from 'app/entities/company/company.service';

@Component({
  selector: 'ak-employee-update',
  templateUrl: './employee-update.component.html'
})
export class EmployeeUpdateComponent implements OnInit {
  isSaving: boolean;

  departments: IDepartment[];

  companies: ICompany[];
  birthdayDp: any;
  identityDateDp: any;

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.maxLength(20)]],
    fullName: [null, [Validators.maxLength(80)]],
    sex: [],
    birthday: [],
    identityCard: [null, [Validators.maxLength(20)]],
    identityDate: [],
    identityIssue: [null, [Validators.maxLength(100)]],
    position: [null, [Validators.maxLength(50)]],
    taxCode: [null, [Validators.maxLength(20)]],
    salary: [],
    salaryRate: [],
    salarySecurity: [],
    numOfDepends: [],
    phone: [null, [Validators.maxLength(20)]],
    mobile: [null, [Validators.maxLength(20)]],
    email: [null, [Validators.maxLength(100), Validators.pattern('^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$')]],
    bankAccount: [null, [Validators.maxLength(20)]],
    bankName: [null, [Validators.maxLength(200)]],
    nodes: [],
    isActive: [],
    timeCreated: [],
    timeModified: [],
    userIdCreated: [],
    userIdModified: [],
    department: [],
    company: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected employeeService: EmployeeService,
    protected departmentService: DepartmentService,
    protected companyService: CompanyService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ employee }) => {
      this.updateForm(employee);
    });
    this.departmentService
      .query()
      .subscribe(
        (res: HttpResponse<IDepartment[]>) => (this.departments = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.companyService
      .query()
      .subscribe((res: HttpResponse<ICompany[]>) => (this.companies = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(employee: IEmployee) {
    this.editForm.patchValue({
      id: employee.id,
      code: employee.code,
      fullName: employee.fullName,
      sex: employee.sex,
      birthday: employee.birthday,
      identityCard: employee.identityCard,
      identityDate: employee.identityDate,
      identityIssue: employee.identityIssue,
      position: employee.position,
      taxCode: employee.taxCode,
      salary: employee.salary,
      salaryRate: employee.salaryRate,
      salarySecurity: employee.salarySecurity,
      numOfDepends: employee.numOfDepends,
      phone: employee.phone,
      mobile: employee.mobile,
      email: employee.email,
      bankAccount: employee.bankAccount,
      bankName: employee.bankName,
      nodes: employee.nodes,
      isActive: employee.isActive,
      timeCreated: employee.timeCreated != null ? employee.timeCreated.format(DATE_TIME_FORMAT) : null,
      timeModified: employee.timeModified != null ? employee.timeModified.format(DATE_TIME_FORMAT) : null,
      userIdCreated: employee.userIdCreated,
      userIdModified: employee.userIdModified,
      department: employee.department,
      company: employee.company
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const employee = this.createFromForm();
    if (employee.id !== undefined) {
      this.subscribeToSaveResponse(this.employeeService.update(employee));
    } else {
      this.subscribeToSaveResponse(this.employeeService.create(employee));
    }
  }

  private createFromForm(): IEmployee {
    return {
      ...new Employee(),
      id: this.editForm.get(['id']).value,
      code: this.editForm.get(['code']).value,
      fullName: this.editForm.get(['fullName']).value,
      sex: this.editForm.get(['sex']).value,
      birthday: this.editForm.get(['birthday']).value,
      identityCard: this.editForm.get(['identityCard']).value,
      identityDate: this.editForm.get(['identityDate']).value,
      identityIssue: this.editForm.get(['identityIssue']).value,
      position: this.editForm.get(['position']).value,
      taxCode: this.editForm.get(['taxCode']).value,
      salary: this.editForm.get(['salary']).value,
      salaryRate: this.editForm.get(['salaryRate']).value,
      salarySecurity: this.editForm.get(['salarySecurity']).value,
      numOfDepends: this.editForm.get(['numOfDepends']).value,
      phone: this.editForm.get(['phone']).value,
      mobile: this.editForm.get(['mobile']).value,
      email: this.editForm.get(['email']).value,
      bankAccount: this.editForm.get(['bankAccount']).value,
      bankName: this.editForm.get(['bankName']).value,
      nodes: this.editForm.get(['nodes']).value,
      isActive: this.editForm.get(['isActive']).value,
      timeCreated:
        this.editForm.get(['timeCreated']).value != null ? moment(this.editForm.get(['timeCreated']).value, DATE_TIME_FORMAT) : undefined,
      timeModified:
        this.editForm.get(['timeModified']).value != null ? moment(this.editForm.get(['timeModified']).value, DATE_TIME_FORMAT) : undefined,
      userIdCreated: this.editForm.get(['userIdCreated']).value,
      userIdModified: this.editForm.get(['userIdModified']).value,
      department: this.editForm.get(['department']).value,
      company: this.editForm.get(['company']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmployee>>) {
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

  trackDepartmentById(index: number, item: IDepartment) {
    return item.id;
  }

  trackCompanyById(index: number, item: ICompany) {
    return item.id;
  }
}

import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'company',
        loadChildren: () => import('./company/company.module').then(m => m.AkCompanyModule)
      },
      {
        path: 'industry',
        loadChildren: () => import('./industry/industry.module').then(m => m.AkIndustryModule)
      },
      {
        path: 'province',
        loadChildren: () => import('./province/province.module').then(m => m.AkProvinceModule)
      },
      {
        path: 'customer',
        loadChildren: () => import('./customer/customer.module').then(m => m.AkCustomerModule)
      },
      {
        path: 'customer-type',
        loadChildren: () => import('./customer-type/customer-type.module').then(m => m.AkCustomerTypeModule)
      },
      {
        path: 'terms',
        loadChildren: () => import('./terms/terms.module').then(m => m.AkTermsModule)
      },
      {
        path: 'store',
        loadChildren: () => import('./store/store.module').then(m => m.AkStoreModule)
      },
      {
        path: 'department',
        loadChildren: () => import('./department/department.module').then(m => m.AkDepartmentModule)
      },
      {
        path: 'jobs',
        loadChildren: () => import('./jobs/jobs.module').then(m => m.AkJobsModule)
      },
      {
        path: 'job-type',
        loadChildren: () => import('./job-type/job-type.module').then(m => m.AkJobTypeModule)
      },
      {
        path: 'employee',
        loadChildren: () => import('./employee/employee.module').then(m => m.AkEmployeeModule)
      },
      {
        path: 'item',
        loadChildren: () => import('./item/item.module').then(m => m.AkItemModule)
      },
      {
        path: 'unit',
        loadChildren: () => import('./unit/unit.module').then(m => m.AkUnitModule)
      },
      {
        path: 'item-group',
        loadChildren: () => import('./item-group/item-group.module').then(m => m.AkItemGroupModule)
      },
      {
        path: 'invoice',
        loadChildren: () => import('./invoice/invoice.module').then(m => m.AkInvoiceModule)
      },
      {
        path: 'invoice-line',
        loadChildren: () => import('./invoice-line/invoice-line.module').then(m => m.AkInvoiceLineModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class AkEntityModule {}

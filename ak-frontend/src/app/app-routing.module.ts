import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: '/welcome' },
  {
    path: 'account',
    loadChildren: () =>
      import('@akfe/account/account.module').then(m => m.AccountModule)
  },
  {
    path: 'admin',
      loadChildren: () =>
        import('@akfe/admin/admin-routing.module').then(m => m.AdminRoutingModule)
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}

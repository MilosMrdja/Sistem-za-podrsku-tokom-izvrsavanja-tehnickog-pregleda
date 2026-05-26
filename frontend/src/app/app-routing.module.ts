import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  { path: '', redirectTo: 'pregled/vozilo', pathMatch: 'full' },
  {
    path: 'pregled',
    loadChildren: () =>
      import('./pages/inspection/inspection.module').then((m) => m.InspectionModule),
  },
  { path: '**', redirectTo: 'pregled/vozilo' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}

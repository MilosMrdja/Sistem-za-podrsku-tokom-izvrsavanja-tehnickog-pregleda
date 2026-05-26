import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { inspectionSessionGuard } from './guards/inspection-session.guard';
import { VehicleInfoPage } from './vehicle-info/vehicle-info.page';
import { PreConditionPage } from './pre-condition/pre-condition.page';
import { IdentificationPage } from './identification/identification.page';
import { WheelTirePage } from './wheel-tire/wheel-tire.page';
import { EngineSystemPage } from './engine-system/engine-system.page';
import { BrakeFluidPage } from './brake-fluid/brake-fluid.page';
import { ExhaustSystemPage } from './exhaust-system/exhaust-system.page';
import { SuspensionSystemPage } from './suspension-system/suspension-system.page';
import { ElectricalSystemPage } from './electrical-system/electrical-system.page';
import { BrakeTestPage } from './brake-test/brake-test.page';
import { LightingSystemPage } from './lighting-system/lighting-system.page';
import { MandatoryEquipmentPage } from './mandatory-equipment/mandatory-equipment.page';
import { SummaryPage } from './summary/summary.page';

const routes: Routes = [
  { path: '', redirectTo: 'vozilo', pathMatch: 'full' },
  { path: 'vozilo', component: VehicleInfoPage },
  { path: 'preduslovi', component: PreConditionPage, canActivate: [inspectionSessionGuard] },
  { path: 'identifikacija', component: IdentificationPage, canActivate: [inspectionSessionGuard] },
  { path: 'gume-tockovi', component: WheelTirePage, canActivate: [inspectionSessionGuard] },
  { path: 'motor', component: EngineSystemPage, canActivate: [inspectionSessionGuard] },
  { path: 'kociona-tecnost', component: BrakeFluidPage, canActivate: [inspectionSessionGuard] },
  { path: 'izduvni', component: ExhaustSystemPage, canActivate: [inspectionSessionGuard] },
  { path: 'ogibljenje', component: SuspensionSystemPage, canActivate: [inspectionSessionGuard] },
  { path: 'elektro', component: ElectricalSystemPage, canActivate: [inspectionSessionGuard] },
  { path: 'kocnice-test', component: BrakeTestPage, canActivate: [inspectionSessionGuard] },
  { path: 'svetla', component: LightingSystemPage, canActivate: [inspectionSessionGuard] },
  { path: 'oprema', component: MandatoryEquipmentPage, canActivate: [inspectionSessionGuard] },
  { path: 'zakljucak', component: SummaryPage },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class InspectionRoutingModule {}

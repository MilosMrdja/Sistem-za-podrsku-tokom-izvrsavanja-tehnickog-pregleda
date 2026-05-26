import { NgModule } from '@angular/core';
import { UiKitModule } from '../../ui-kit/ui-kit.module';
import { InspectionRoutingModule } from './inspection-routing.module';
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

@NgModule({
  declarations: [
    VehicleInfoPage,
    PreConditionPage,
    IdentificationPage,
    WheelTirePage,
    EngineSystemPage,
    BrakeFluidPage,
    ExhaustSystemPage,
    SuspensionSystemPage,
    ElectricalSystemPage,
    BrakeTestPage,
    LightingSystemPage,
    MandatoryEquipmentPage,
    SummaryPage,
  ],
  imports: [UiKitModule, InspectionRoutingModule],
})
export class InspectionModule {}

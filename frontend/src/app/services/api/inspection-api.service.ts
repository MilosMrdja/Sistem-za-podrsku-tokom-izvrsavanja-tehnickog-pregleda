import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { InspectionResponseDto } from '../../dto/response/inspection-response.dto';
import { VehicleDto } from '../../dto/request/vehicle.dto';
import { InspectionConditionsDto } from '../../dto/request/inspection-conditions.dto';
import { VehicleIdentificationDto } from '../../dto/request/vehicle-identification.dto';
import { WheelTireDto } from '../../dto/request/wheel-tire.dto';
import { EngineSystemDto } from '../../dto/request/engine-system.dto';
import { BrakeFluidDto } from '../../dto/request/brake-fluid.dto';
import { ExhaustMeasurementDto } from '../../dto/request/exhaust-measurement.dto';
import { SuspensionSystemDto } from '../../dto/request/suspension-system.dto';
import { ElectricalSystemDto } from '../../dto/request/electrical-system.dto';
import { LightingSystemDto } from '../../dto/request/lighting-system.dto';
import { MandatoryEquipmentDto } from '../../dto/request/mandatory-equipment.dto';

@Injectable({ providedIn: 'root' })
export class InspectionApiService {
  private readonly base = environment.apiBaseUrl;

  constructor(private readonly http: HttpClient) {}

  createInspection(body: VehicleDto): Observable<InspectionResponseDto> {
    return this.http.post<InspectionResponseDto>(this.base, body);
  }

  preCondition(
    body: InspectionConditionsDto,
  ): Observable<InspectionResponseDto> {
    return this.http.post<InspectionResponseDto>(
      `${this.base}/pre-condition`,
      body,
    );
  }

  identification(
    body: VehicleIdentificationDto,
  ): Observable<InspectionResponseDto> {
    return this.http.post<InspectionResponseDto>(
      `${this.base}/identification`,
      body,
    );
  }

  wheelTire(body: WheelTireDto): Observable<InspectionResponseDto> {
    console.log(body);
    return this.http.post<InspectionResponseDto>(
      `${this.base}/wheel-tire`,
      body,
    );
  }

  engineSystem(body: EngineSystemDto): Observable<InspectionResponseDto> {
    return this.http.post<InspectionResponseDto>(
      `${this.base}/engine-system`,
      body,
    );
  }

  brakeFluid(body: BrakeFluidDto): Observable<InspectionResponseDto> {
    return this.http.post<InspectionResponseDto>(
      `${this.base}/brake-fluid`,
      body,
    );
  }

  exhaustSystem(
    body: ExhaustMeasurementDto,
  ): Observable<InspectionResponseDto> {
    return this.http.post<InspectionResponseDto>(
      `${this.base}/exhaust-system`,
      body,
    );
  }

  suspensionSystem(
    body: SuspensionSystemDto,
  ): Observable<InspectionResponseDto> {
    return this.http.post<InspectionResponseDto>(
      `${this.base}/suspension-system`,
      body,
    );
  }

  electricalSystem(
    body: ElectricalSystemDto,
  ): Observable<InspectionResponseDto> {
    return this.http.post<InspectionResponseDto>(
      `${this.base}/electrical-system`,
      body,
    );
  }

  startBrakeTest(inspectionId: number): Observable<InspectionResponseDto> {
    return this.http.post<InspectionResponseDto>(
      `${this.base}/start-brake-test/${inspectionId}`,
      {},
    );
  }

  getBrakeTestStatus(inspectionId: number): Observable<InspectionResponseDto> {
    return this.http.get<InspectionResponseDto>(
      `${this.base}/${inspectionId}/status`,
    );
  }

  lightingSystem(body: LightingSystemDto): Observable<InspectionResponseDto> {
    return this.http.post<InspectionResponseDto>(
      `${this.base}/lighting-system`,
      body,
    );
  }

  mandatoryEquipment(
    body: MandatoryEquipmentDto,
  ): Observable<InspectionResponseDto> {
    return this.http.post<InspectionResponseDto>(
      `${this.base}/mandatory-equipment`,
      body,
    );
  }
}

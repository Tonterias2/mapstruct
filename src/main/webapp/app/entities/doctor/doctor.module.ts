import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MapstructSharedModule } from 'app/shared/shared.module';
import { DoctorComponent } from './doctor.component';
import { DoctorDetailComponent } from './doctor-detail.component';
import { DoctorUpdateComponent } from './doctor-update.component';
import { DoctorDeleteDialogComponent } from './doctor-delete-dialog.component';
import { doctorRoute } from './doctor.route';

@NgModule({
  imports: [MapstructSharedModule, RouterModule.forChild(doctorRoute)],
  declarations: [DoctorComponent, DoctorDetailComponent, DoctorUpdateComponent, DoctorDeleteDialogComponent],
  entryComponents: [DoctorDeleteDialogComponent],
})
export class MapstructDoctorModule {}

import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'doctor',
        loadChildren: () => import('./doctor/doctor.module').then(m => m.MapstructDoctorModule),
      },
      {
        path: 'education',
        loadChildren: () => import('./education/education.module').then(m => m.MapstructEducationModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class MapstructEntityModule {}

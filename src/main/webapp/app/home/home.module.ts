import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MapstructSharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';

@NgModule({
  imports: [MapstructSharedModule, RouterModule.forChild([HOME_ROUTE])],
  declarations: [HomeComponent],
})
export class MapstructHomeModule {}

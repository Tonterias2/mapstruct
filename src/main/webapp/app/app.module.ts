import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { MapstructSharedModule } from 'app/shared/shared.module';
import { MapstructCoreModule } from 'app/core/core.module';
import { MapstructAppRoutingModule } from './app-routing.module';
import { MapstructHomeModule } from './home/home.module';
import { MapstructEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    MapstructSharedModule,
    MapstructCoreModule,
    MapstructHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    MapstructEntityModule,
    MapstructAppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent],
})
export class MapstructAppModule {}

import { BrowserModule } from '@angular/platform-browser';
import { NgModule, Optional, SkipSelf } from '@angular/core';

import { EnsureModuleLoadedOnceGuard } from './guards/ensure-module-loaded-once.guard';
import { CoreModule } from './core.module';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { LogoutComponent } from './logout/logout.component';

@NgModule({
  declarations: [
    AppComponent,
    PageNotFoundComponent,
    LogoutComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    CoreModule
  ],
  bootstrap: [AppComponent]
})
export class AppModule extends EnsureModuleLoadedOnceGuard {
  public constructor(@SkipSelf() @Optional() parentModule: CoreModule) {
    super(parentModule);
  }
}

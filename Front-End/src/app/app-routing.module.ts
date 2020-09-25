import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuthenticationGuard } from './guards/authentication.guard';
import { LogoutComponent } from './logout/logout.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { PreloadTaggedModuleStrategy } from './preload-tagged-module-strategy';
import { LogoutResolver } from './services/authentication/logout-resolver.service';

const routes: Routes = [
  {
    path: 'login',
    loadChildren: () => import('./login/login.module').then(m => m.LoginModule)
  },
  {
    path: 'logout',
    component: LogoutComponent,
    resolve: { isDisconnected: LogoutResolver }
  },
  {
    path: 'customer-area',
    loadChildren: () => import('./home/home.module').then(m => m.HomeModule),
    data: { preload: true },
    canActivate: [
      AuthenticationGuard
    ]
  },
  {
    path: '',
    redirectTo: 'customer-area',
    pathMatch: 'full'
  },
  {
    path: '**',
    component: PageNotFoundComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { preloadingStrategy: PreloadTaggedModuleStrategy })],
  providers: [PreloadTaggedModuleStrategy],
  exports: [RouterModule]
})
export class AppRoutingModule { }

import { NgModule } from '@angular/core';
import { Routes, RouterModule, PreloadAllModules } from '@angular/router';
import { AuthenticationGuard } from './guards/authentication.guard';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { PreloadTaggedModuleStrategy } from './preload-tagged-module-strategy';

const routes: Routes = [
  {
    path: 'login',
    loadChildren: () => import('./login/login.module').then(m => m.LoginModule)
  },
  {
    path: 'home',
    loadChildren: () => import('./home/home.module').then(m => m.HomeModule),
    data: { preload: true },
    canActivate: [
      AuthenticationGuard
    ]
  },
  {
    path: '',
    redirectTo: 'home',
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

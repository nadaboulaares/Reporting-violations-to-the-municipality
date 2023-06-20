
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { HttpClientModule,HTTP_INTERCEPTORS } from '@angular/common/http';

import { AppComponent } from './app.component';
import { AdminComponent } from './admin/admin.component';
import { SignUpComponent } from './admin/sign-up/sign-up.component';

import { appRoutes } from './routes';
import { AdminProfileComponent } from './admin-profile/admin-profile.component';
import { SignInComponent } from './admin/sign-in/sign-in.component';
import { AdminService } from './shared/admin.service';

import { AuthGuard } from './auth/auth.guard';
import { AuthInterceptor } from './auth/auth.interceptor';
import { ReclamationComponent } from './reclamation/reclamation.component';

import{ AgmCoreModule} from "@agm/core";
import { MapComponent } from './map/map.component';


import { ReclamationRejeteeComponent } from './reclamationrejetee/reclamationrejetee.component';
import { ReclamationvalideComponent } from './reclamationvalide/reclamationvalide.component';

@NgModule({
  declarations: [
    AppComponent,
    AdminComponent,
    SignUpComponent,
    AdminProfileComponent,
    ReclamationComponent,
    SignInComponent,
    MapComponent,
  
 
    ReclamationRejeteeComponent,
 
    ReclamationvalideComponent,
  
   
   
  ],
  imports: [
    BrowserModule,
    FormsModule,
    RouterModule.forRoot(appRoutes),
    HttpClientModule,
    AgmCoreModule.forRoot({
      apiKey: 'AIzaSyB6H1NB2rcVXGddDdFmOlF0FjyRbfiRJfY'
    }),
  
  ],
  providers: [{
    provide: HTTP_INTERCEPTORS,
    useClass: AuthInterceptor,
    multi: true
  },AuthGuard,AdminService],
  bootstrap: [AppComponent]
})
export class AppModule { }

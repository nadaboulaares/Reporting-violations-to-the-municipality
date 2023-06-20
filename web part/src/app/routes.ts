import { Routes } from '@angular/router';
import { AdminComponent } from './admin/admin.component';
import { SignUpComponent } from './admin/sign-up/sign-up.component';
import { SignInComponent } from './admin/sign-in/sign-in.component';
import { AdminProfileComponent } from './admin-profile/admin-profile.component';
import { AuthGuard } from './auth/auth.guard';
import {ReclamationComponent} from './reclamation/reclamation.component';
import {MapComponent} from './map/map.component';
import {ReclamationvalideComponent} from './reclamationvalide/reclamationvalide.component';
import {ReclamationRejeteeComponent} from './reclamationrejetee/reclamationrejetee.component';
export const appRoutes: Routes = [
    {
        path: 'signup', component: AdminComponent,
        children: [{ path: '', component: SignUpComponent }]
    },
 
    {
        path: 'reclamation', component: ReclamationComponent,
        children: [{ path: '', component: ReclamationComponent }]
    },
    {
        path: 'reclamationrejetee', component: ReclamationRejeteeComponent,
        children: [{ path: '', component: ReclamationRejeteeComponent }]
    },
    {
        path: 'reclamationvalide', component: ReclamationvalideComponent,
        children: [{ path: '', component: ReclamationvalideComponent }]
    },
    {
        path: 'map', component: MapComponent,
        children: [{ path: '', component: MapComponent }]
    },
    {
        path: 'login', component: AdminComponent,
        children: [{ path: '', component: SignInComponent }]
    },
    {
        path: 'adminprofile', component: AdminProfileComponent,canActivate:[AuthGuard]
    },
  
    {
        path: '', redirectTo: '/login', pathMatch: 'full'
    }
];
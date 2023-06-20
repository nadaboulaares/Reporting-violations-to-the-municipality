import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { environment } from '../../environments/environment';
import { Admin } from './admin.model';

@Injectable({
  providedIn: 'root'
})
export class AdminService {
  selectedAdmin: Admin = {
    Name: '',
    email: '',
    password: ''
  };

  noAuthHeader = { headers: new HttpHeaders({ 'NoAuth': 'True' }) };

  constructor(private http: HttpClient) { }


  postAdmin(admin: Admin){
    return this.http.post(environment.apiBaseUrl+'/register',admin,this.noAuthHeader);
  }
 

  login(authCredentials) {
    return this.http.post(environment.apiBaseUrl + '/authenticate', authCredentials,this.noAuthHeader);
  }

  getAdminProfile() {
    return this.http.get(environment.apiBaseUrl + '/adminProfile');
  }




  setToken(token: string) {
    localStorage.setItem('token', token);
  }

  getToken() {
    return localStorage.getItem('token');
  }

  deleteToken() {
    localStorage.removeItem('token');
  }

  getAdminPayload() {
    var token = this.getToken();
    if (token) {
      var adminPayload = atob(token.split('.')[1]);
      return JSON.parse(adminPayload);
    }
    else
      return null;
  }

  isLoggedIn() {
    var adminPayload = this.getAdminPayload();
    if (adminPayload)
      return adminPayload.exp > Date.now() / 1000;
    else
      return false;
  }
}

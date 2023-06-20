import { Component, OnInit } from '@angular/core';
import { NgForm } from "@angular/forms";
import { Router } from "@angular/router";

import { AdminService } from '../../shared/admin.service';

@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.css']
})
export class SignInComponent implements OnInit {

  constructor(private adminService: AdminService,private router : Router) { }

  model ={
    email :'',
    password:''
  };
  emailRegex = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
  serverErrorMessages: string;
  ngOnInit() {
    if(this.adminService.isLoggedIn())
    this.router.navigateByUrl('/adminprofile');
  }

  onSubmit(form : NgForm){
    this.adminService.login(form.value).subscribe(
      res => {
        this.adminService.setToken(res['token']);
        this.router.navigateByUrl('/adminprofile');
        
      },
      err => {
        this.serverErrorMessages = err.error.message;
      }
    );
  }

}

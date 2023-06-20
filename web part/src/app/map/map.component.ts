import { Component, OnInit } from '@angular/core';


import{ AgmCoreModule} from "@agm/core";

import { NgForm } from '@angular/forms';

import { ReclamationService } from '../shared/reclamation.service';

import { Reclamation } from '../shared/reclamation.model';
import { AdminService } from '../shared/admin.service';
import { Router } from "@angular/router";
@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css'],
  providers: [ReclamationService],
})
export class MapComponent implements OnInit {
  adminDetails;
  latitude =  36.4560600;
  longitude = 10.7376300;
  constructor(private reclamationService: ReclamationService, private adminService: AdminService , private router: Router) { }

  ngOnInit() {
 
    this.refreshReclamationList();
    
  }







refreshReclamationList() {
  this.reclamationService.getReclamationList().subscribe((res) => {
    this.reclamationService.reclamations = res as Reclamation[];
  });
}
  

  onLogout(){
    this.adminService.deleteToken();
    this.router.navigate(['/login']);
  }
  onChoseLocation(event){
console .log(event);
  }
  
}

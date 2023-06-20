import { Component, OnInit } from '@angular/core';
import { AdminService } from '../shared/admin.service';
import { Router } from "@angular/router";
import { ReclamationService } from '../shared/reclamation.service';

import { Reclamation } from '../shared/reclamation.model';
import{ AgmCoreModule} from "@agm/core";
@Component({
  selector: 'app-admin-profile',
  templateUrl: './admin-profile.component.html',
  styleUrls: ['./admin-profile.component.css'],
  providers: [ReclamationService],
})
export class AdminProfileComponent implements OnInit {
  adminDetails;
  myDate=Date.now();
  constructor(private reclamationService: ReclamationService, private adminService: AdminService, private router: Router) { }

  ngOnInit() {
    this.adminService.getAdminProfile().subscribe(
      res => {
        this.adminDetails = res['admin'];
      },
      err => {
        console.log(err);
      }
    );
    this.refreshReclamationList();
  }

   refreshReclamationList() {
    this.reclamationService.getReclamationList().subscribe((res) => {
      this.reclamationService.reclamations = res as Reclamation[];
    });
  }

  valideeReclamationsStatistic() {
    // This is the number of reclamations
    const reclamationCount = this.reclamationService.reclamations.length;

    // This is the number of reclamations where validée === 'etat'
    let valideeReclamationCount = 0;

    // We loop the reclamations and set valideeReclamationCount + 1 each
    // time we find one
    this.reclamationService.reclamations.forEach((reclamation) => {
      if (reclamation.etat === 'validée') {
        valideeReclamationCount++;
      }
    });

    // Final statistic
    return Math.round(valideeReclamationCount / reclamationCount * 100 ).toFixed(2);
  }
  rejeteeReclamationsStatistic() {
    // This is the number of reclamations
    const reclamationCount = this.reclamationService.reclamations.length;

    // This is the number of reclamations where validée === 'etat'
    let rejeteeReclamationCount = 0;

    // We loop the reclamations and set valideeReclamationCount + 1 each
    // time we find one
    this.reclamationService.reclamations.forEach((reclamation) => {
      if (reclamation.etat === 'rejetée') {
        rejeteeReclamationCount++;
      }
    });

    // Final statistic
    return Math.round(rejeteeReclamationCount / reclamationCount * 100).toFixed(2);
  }
  attenteReclamationsStatistic() {
    // This is the number of reclamations
    const reclamationCount = this.reclamationService.reclamations.length;

    // This is the number of reclamations where validée === 'etat'
    let attenteReclamationCount = 0;

  
    this.reclamationService.reclamations.forEach((reclamation) => {
      if (reclamation.etat === 'en attente') {
        attenteReclamationCount++;
      }
    });

 
    return Math.round(attenteReclamationCount / reclamationCount * 100).toFixed(2);
  }
  onLogout() {
    this.adminService.deleteToken();
    this.router.navigate(['/login']);
  }

}
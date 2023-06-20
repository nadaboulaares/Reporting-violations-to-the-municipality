import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';

import { ReclamationService } from '../shared/reclamation.service';

import { Reclamation } from '../shared/reclamation.model';
import { AdminService } from '../shared/admin.service';
import { Router } from "@angular/router";


declare var M: any;
@Component({
  selector: 'app-reclamationrejetee',
  templateUrl: './reclamationrejetee.component.html',
  styleUrls: ['./reclamationrejetee.component.css'],
  providers: [ReclamationService],
})
export class ReclamationRejeteeComponent implements OnInit {

  constructor(private reclamationService: ReclamationService, private adminService: AdminService , private router: Router) { }

  ngOnInit() {
 
    this.refreshReclamationList();
    
  }
  public showMyMessage = false  
  refreshReclamationList() {
    this.reclamationService.getReclamationList().subscribe((res) => {
      this.reclamationService.reclamations = res as Reclamation[];
    });
  }
  
  onEdit(_id: string, reclamation: Reclamation) {
  
    reclamation.etat='validée';
    reclamation._id = _id;
  
   this.reclamationService.putReclamation(_id , reclamation).subscribe((res) => {
     this.reclamationService.selectedReclamation.etat=reclamation.etat;
     this.refreshReclamationList();
     console.log("reclamtion validée!");
   });
  }
  
  Edit(_id: string, reclamation: Reclamation) {
  
    reclamation.etat='rejetée';
    reclamation._id = _id;
  
   this.reclamationService.putReclamation(_id , reclamation).subscribe((res) => {
     this.reclamationService.selectedReclamation.etat=reclamation.etat;
     this.refreshReclamationList();
   
  
      console.log("reclamtion rejetée!");
    });
  }
   
   
  
  
  onDelete(_id: string, form: NgForm) {
    if (confirm('Êtes-vous sûr de vouloir supprimer cette réclamation !?') == true) {
      this.reclamationService.deleteReclamation(_id).subscribe((res) => {
        this.refreshReclamationList();
  
        this.showMessageSoon();  
      });
    }
  
  }
  
  showMessageSoon() {
    this.showMyMessage = true;  
    setTimeout(() => {
      this.showMyMessage = false
    }, 3000)
   
  }
  onLogout(){
    this.adminService.deleteToken();
    this.router.navigate(['/login']);
  }
   
}

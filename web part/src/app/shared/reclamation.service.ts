import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';



import { Reclamation } from './reclamation.model';
@Injectable()



export class ReclamationService {
  selectedReclamation: Reclamation;
  reclamations: Reclamation[];
  readonly baseURL = 'http://localhost:3000/reclamation';

  constructor(private http: HttpClient) { }

  postReclamation(reclamation: Reclamation) {
    return this.http.post(this.baseURL, reclamation);
  }

  getReclamationList() {
    return this.http.get(this.baseURL);
  }

  putReclamation(_id: string, reclamation: Reclamation) {
    return this.http.put(this.baseURL + `/${reclamation.etat}`, reclamation);
  }

  deleteReclamation(_id: string) {
    return this.http.delete(this.baseURL + `/${_id}`);
  }
  getReclamationValide(reclamation: Reclamation){
    return this.http.get(this.baseURL );
  }
 
}
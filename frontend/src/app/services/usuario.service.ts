import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {
  private apiUrl = 'http://localhost:8081/api/auth';

  constructor(private http: HttpClient) {}

  login(input: string, password: string) {
    return this.http.post(`${this.apiUrl}/login`, { input, password }, { responseType: 'text' });
  }
  
  
  

  register(usuario: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/register`, usuario);
  }
}

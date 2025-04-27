import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private TOKEN_KEY = 'token';

  constructor() {}

  setToken(token: string): void {
    console.log("Token antes de guardar:", token);
    localStorage.setItem(this.TOKEN_KEY, token);
  }
  

  getToken(): string | null {
    return localStorage.getItem(this.TOKEN_KEY);
  }

  logout(): void {
    localStorage.removeItem('token');
  }

  
  
}

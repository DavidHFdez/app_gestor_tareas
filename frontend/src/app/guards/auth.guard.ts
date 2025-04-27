import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  constructor(private router: Router) {}

  canActivate(): boolean {
    const token = localStorage.getItem('token'); // Recuperar el token

    if (token) {
      console.log('Usuario autenticado. Acceso permitido.');
      return true;
    } else {
      console.warn('Usuario NO autenticado. Redirigiendo a login...');
      this.router.navigate(['/login']);
      return false;
    }
  }
}

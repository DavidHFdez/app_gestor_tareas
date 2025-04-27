import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { UsuarioService } from '../../services/usuario.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common'; // ✅ Este es el importante

@Component({
  selector: 'app-login',
  standalone: true,
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
  imports: [FormsModule, CommonModule]
})
export class LoginComponent {

  // Login
  loginInput: string = ''; // Usuario, email o teléfono
  password: string = '';

  // Registro
  regUsername: string = '';
  regEmail: string = '';
  regPassword: string = '';
  regTelefono: string = '';

  // Estados UI
  showLoginPassword: boolean = false;
  showRegisterPassword: boolean = false;
  showRegister: boolean = false;

  constructor(
    private usuarioService: UsuarioService,
    private router: Router
  ) {}

  // Mostrar/Ocultar contraseña en login
  toggleLoginPassword(): void {
    this.showLoginPassword = !this.showLoginPassword;
  }

  // Mostrar/Ocultar contraseña en registro
  toggleRegisterPassword(): void {
    this.showRegisterPassword = !this.showRegisterPassword;
  }

  // Alternar entre formularios
  toggleForms(): void {
    this.showRegister = !this.showRegister;
  }

  // Método para iniciar sesión
  login(): void {
    const credentials = {
      input: this.loginInput,
      password: this.password
    };

    console.log("Enviando credenciales:", credentials);

    this.usuarioService.login(credentials.input, credentials.password).subscribe({
      next: (token: string) => {
        console.log("oken recibido:", token);
        localStorage.setItem('token', token);
        this.router.navigate(['/home']);
      },
      error: (err) => {
        console.error("Error en login:", err);
        alert('Credenciales incorrectas');
      }
    });
  }

  // Método para registrar usuario y loguearlo automáticamente
  register(): void {
    const nuevoUsuario = {
      username: this.regUsername,
      email: this.regEmail,
      password: this.regPassword,
      telefono: this.regTelefono
    };

    this.usuarioService.register(nuevoUsuario).subscribe({
      next: () => {
        // Login automático tras registro
        this.usuarioService.login(this.regEmail, this.regPassword).subscribe({
          next: (token: string) => {
            localStorage.setItem('token', token);
            this.router.navigate(['/home']);
          },
          error: () => {
            alert('Registro exitoso, pero hubo un problema al iniciar sesión.');
          }
        });
      },
      error: () => {
        alert('Error al registrar usuario');
      }
    });
  }
}

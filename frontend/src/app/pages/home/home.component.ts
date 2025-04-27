import { Component, OnInit } from '@angular/core';
import { TareaService } from '../../services/tarea.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
  standalone: true,
  imports: [FormsModule, CommonModule]
})
export class HomeComponent implements OnInit {
  tareas: any[] = []; // Lista de todas las tareas
  tareasFiltradas: any[] = []; // Tareas después de aplicar filtros

  nuevaTarea = {
    titulo: '',
    descripcion: '',
    fechaCreacion: new Date(),
    fechaVencimiento: '',
    categoria: '',
    estado: 'pendiente',
    prioridad: 'media',
    usuarioId: 1
  };

  filtros = {
    estado: '',
    categoria: '',
    titulo: ''
  };

  orden = ''; // Orden actual aplicado
  tareaEnEdicion: any = null; // Tarea seleccionada para editar

  constructor(
    private tareaService: TareaService,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.cargarTareas(); // Al iniciar carga las tareas
  }

  cargarTareas() {
    this.tareaService.obtenerTareas().subscribe({
      next: (data) => {
        this.tareas = data;
        this.aplicarFiltrosYOrden();
      },
      error: (err) => {
        console.error('Error al obtener tareas:', err);
      }
    });
  }

  agregarTarea() {
    console.log("Creando tarea:", this.nuevaTarea);
    this.tareaService.crearTarea(this.nuevaTarea).subscribe({
      next: (tarea) => {
        this.tareas.push(tarea);
        this.aplicarFiltrosYOrden();
        alert('Tarea creada con éxito');
        // Reiniciar el formulario de nueva tarea
        this.nuevaTarea = {
          titulo: '',
          descripcion: '',
          fechaCreacion: new Date(),
          fechaVencimiento: '',
          categoria: '',
          estado: 'pendiente',
          prioridad: 'media',
          usuarioId: 1
        };
      },
      error: (err) => {
        console.error('Error al crear tarea:', err);
      }
    });
  }

  eliminarTarea(id: number) {
    if (confirm('¿Estás seguro de que quieres eliminar esta tarea?')) {
      this.tareaService.eliminarTarea(id).subscribe({
        next: () => {
          this.tareas = this.tareas.filter(tarea => tarea.id !== id);
          this.aplicarFiltrosYOrden();
          alert('Tarea eliminada con éxito');
        },
        error: (err) => {
          console.error('Error al eliminar tarea:', err);
        }
      });
    }
  }

  aplicarFiltrosYOrden() {
    let filtradas = this.tareas;

    if (this.filtros.titulo) {
      filtradas = filtradas.filter(t =>
        t.titulo.toLowerCase().includes(this.filtros.titulo.toLowerCase())
      );
    }

    if (this.filtros.estado) {
      filtradas = filtradas.filter(t => t.estado === this.filtros.estado);
    }

    if (this.filtros.categoria) {
      filtradas = filtradas.filter(t =>
        t.categoria.toLowerCase().includes(this.filtros.categoria.toLowerCase())
      );
    }

    if (this.orden === 'fecha') {
      filtradas = filtradas.sort((a, b) =>
        new Date(a.fechaVencimiento).getTime() - new Date(b.fechaVencimiento).getTime()
      );
    } else if (this.orden === 'prioridad') {
      const prioridadValor: any = { baja: 1, media: 2, alta: 3 };
      filtradas = filtradas.sort((a, b) =>
        prioridadValor[b.prioridad] - prioridadValor[a.prioridad]
      );
    }

    this.tareasFiltradas = filtradas;
  }

  editarTarea(tarea: any) {
    this.tareaEnEdicion = { ...tarea }; // Copia la tarea para editar sin modificar el original
  }

  guardarCambios() {
    this.tareaService.actualizarTarea(this.tareaEnEdicion.id, this.tareaEnEdicion).subscribe({
      next: (tareaActualizada) => {
        const index = this.tareas.findIndex(t => t.id === tareaActualizada.id);
        this.tareas[index] = tareaActualizada;
        this.aplicarFiltrosYOrden();
        this.tareaEnEdicion = null;
        alert('Tarea actualizada con éxito');
      },
      error: (err) => {
        console.error('Error al actualizar tarea:', err);
      }
    });
  }

  getClaseEstado(estado: string): string {
    return 'estado-' + estado.toLowerCase().replace(/\s+/g, '-');
  }

  getClasePrioridad(prioridad: string): string {
    return 'prioridad-' + prioridad.toLowerCase();
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}

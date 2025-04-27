import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class TareaService {
  private apiUrl = 'http://localhost:8082/api/tareas';

  constructor(private http: HttpClient) {}

  private getHeaders() {
    const token = localStorage.getItem('token');
    return {
      headers: new HttpHeaders({
        Authorization: `Bearer ${token}`,
        'Content-Type': 'application/json'
      })
    };
  }

  obtenerTareas() {
    return this.http.get<any[]>(this.apiUrl, this.getHeaders());
  }

  crearTarea(tarea: any) {
    return this.http.post(this.apiUrl, tarea, this.getHeaders());
  }

  eliminarTarea(id: number) {
    return this.http.delete(`${this.apiUrl}/${id}`, this.getHeaders());
  }

  actualizarTarea(id: number, tarea: any) {
    return this.http.put<any>(`${this.apiUrl}/${id}`, tarea, this.getHeaders());
  }
  
  

}

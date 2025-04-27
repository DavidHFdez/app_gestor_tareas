// Entidad JPA que representa una tarea en la base de datos

package com.gestor_tareas.tareas_service.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tareas")
public class Tarea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Genera ID automáticamente
    private Long id;

    private String titulo;
    private String descripcion;
    private LocalDate fechaCreacion;
    private LocalDate fechaVencimiento;

    private String categoria;
    private String estado;
    private String prioridad;

    private Long usuarioId;

    public Tarea() {}

    // Constructor con todos los campos
    public Tarea(String titulo, String descripcion, LocalDate fechaCreacion, LocalDate fechaVencimiento,
                 String categoria, String estado, String prioridad, Long usuarioId) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaCreacion = fechaCreacion;
        this.fechaVencimiento = fechaVencimiento;
        this.categoria = categoria;
        this.estado = estado;
        this.prioridad = prioridad;
        this.usuarioId = usuarioId;
    }

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public LocalDate getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDate fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public LocalDate getFechaVencimiento() { return fechaVencimiento; }
    public void setFechaVencimiento(LocalDate fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getPrioridad() { return prioridad; }
    public void setPrioridad(String prioridad) { this.prioridad = prioridad; }

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
}

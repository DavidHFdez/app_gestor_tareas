// Repositorio JPA para acceder a las tareas desde la base de datos

package com.gestor_tareas.tareas_service.repository;

import com.gestor_tareas.tareas_service.model.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TareaRepository extends JpaRepository<Tarea, Long> {

    // Encuentra todas las tareas asociadas a un usuario
    List<Tarea> findByUsuarioId(Long usuarioId);
}

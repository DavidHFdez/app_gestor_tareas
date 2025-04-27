// Controlador REST que expone la API de tareas para crear, listar, editar y borrar

package com.gestor_tareas.tareas_service.controller;

import com.gestor_tareas.tareas_service.model.Tarea;
import com.gestor_tareas.tareas_service.security.JwtUtil;
import com.gestor_tareas.tareas_service.service.TareaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tareas")
public class TareaController {

    private final TareaService tareaService;
    private final JwtUtil jwtUtil;

    public TareaController(TareaService tareaService, JwtUtil jwtUtil) {
        this.tareaService = tareaService;
        this.jwtUtil = jwtUtil;
    }

    // GET /api/tareas - Lista todas las tareas del usuario autenticado
    @GetMapping
    public ResponseEntity<List<Tarea>> obtenerTareasDelUsuario(@RequestHeader("Authorization") String token) {
        Long usuarioId = jwtUtil.getUserIdFromToken(token.replace("Bearer ", ""));
        if (usuarioId == null) {
            return ResponseEntity.status(401).build();
        }
        List<Tarea> tareas = tareaService.obtenerPorUsuario(usuarioId);
        return ResponseEntity.ok(tareas);
    }

    // POST /api/tareas - Crea una nueva tarea asociada al usuario autenticado
    @PostMapping
    public ResponseEntity<Tarea> crearTarea(@RequestBody Tarea tarea, @RequestHeader("Authorization") String token) {
        Long usuarioId = jwtUtil.getUserIdFromToken(token.replace("Bearer ", ""));
        if (usuarioId == null) {
            return ResponseEntity.status(401).build();
        }
        tarea.setUsuarioId(usuarioId);
        Tarea nuevaTarea = tareaService.guardarTarea(tarea);
        return ResponseEntity.ok(nuevaTarea);
    }

    // GET /api/tareas/{id} - Obtiene una tarea por ID
    @GetMapping("/{id}")
    public ResponseEntity<Tarea> obtenerPorId(@PathVariable Long id) {
        return tareaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/tareas/{id} - Elimina una tarea
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTarea(@PathVariable Long id) {
        tareaService.eliminarTarea(id);
        return ResponseEntity.noContent().build();
    }

    // PUT /api/tareas/{id} - Actualiza una tarea existente
    @PutMapping("/{id}")
    public ResponseEntity<Tarea> actualizarTarea(@PathVariable Long id, @RequestBody Tarea tareaActualizada) {
        return tareaService.actualizarTarea(id, tareaActualizada)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}

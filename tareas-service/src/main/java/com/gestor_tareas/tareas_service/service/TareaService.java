// Servicio que gestiona operaciones CRUD sobre las tareas del usuario

package com.gestor_tareas.tareas_service.service;

import com.gestor_tareas.tareas_service.model.Tarea;
import com.gestor_tareas.tareas_service.repository.TareaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TareaService {

    private final TareaRepository tareaRepository;

    public TareaService(TareaRepository tareaRepository) {
        this.tareaRepository = tareaRepository;
    }

    // Devuelve todas las tareas del usuario según su ID
    public List<Tarea> obtenerPorUsuario(Long usuarioId) {
        return tareaRepository.findByUsuarioId(usuarioId);
    }

    // Busca una tarea específica por su ID
    public Optional<Tarea> obtenerPorId(Long id) {
        return tareaRepository.findById(id);
    }

    // Guarda una nueva tarea en la base de datos
    public Tarea guardarTarea(Tarea tarea) {
        return tareaRepository.save(tarea);
    }

    // Elimina una tarea existente por su ID
    public void eliminarTarea(Long id) {
        tareaRepository.deleteById(id);
    }

    // Actualiza los campos de una tarea existente, si la encuentra
    public Optional<Tarea> actualizarTarea(Long id, Tarea tareaActualizada) {
        return tareaRepository.findById(id).map(t -> {
            t.setTitulo(tareaActualizada.getTitulo());
            t.setDescripcion(tareaActualizada.getDescripcion());
            t.setCategoria(tareaActualizada.getCategoria());
            t.setPrioridad(tareaActualizada.getPrioridad());
            t.setEstado(tareaActualizada.getEstado());
            t.setFechaVencimiento(tareaActualizada.getFechaVencimiento());
            return tareaRepository.save(t); // Guarda la tarea actualizada
        });
    }
}

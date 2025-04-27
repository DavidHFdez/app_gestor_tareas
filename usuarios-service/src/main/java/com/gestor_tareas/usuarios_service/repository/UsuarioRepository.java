// Repositorio que permite acceder a usuarios por email, username o teléfono

package com.gestor_tareas.usuarios_service.repository;

import com.gestor_tareas.usuarios_service.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email); // Búsqueda por email
    Optional<Usuario> findByUsername(String username); // Búsqueda por username
    Optional<Usuario> findByTelefono(String telefono); // Búsqueda por teléfono
}

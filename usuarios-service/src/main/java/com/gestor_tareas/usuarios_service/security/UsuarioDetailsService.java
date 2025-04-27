// Servicio que carga los detalles de un usuario desde la base de datos usando email, username o teléfono

package com.gestor_tareas.usuarios_service.security;

import com.gestor_tareas.usuarios_service.model.Usuario;
import com.gestor_tareas.usuarios_service.repository.UsuarioRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UsuarioDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String input) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(input) // Buscar por email
            .or(() -> usuarioRepository.findByUsername(input))  // Si no, buscar por username
            .or(() -> usuarioRepository.findByTelefono(input))  // Si no, buscar por teléfono
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + input)); // Si no se encuentra, lanzar excepción

        return new User(
            usuario.getEmail(), // Usamos el email como username
            usuario.getPassword(), // Password encriptado
            Collections.emptyList() // No asignamos roles
        );
    }
}

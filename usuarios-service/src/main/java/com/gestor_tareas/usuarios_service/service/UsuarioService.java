// Servicio que gestiona el registro y autenticación de usuarios

package com.gestor_tareas.usuarios_service.service;

import com.gestor_tareas.usuarios_service.model.AuthRequest;
import com.gestor_tareas.usuarios_service.model.Usuario;
import com.gestor_tareas.usuarios_service.repository.UsuarioRepository;
import com.gestor_tareas.usuarios_service.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, 
                          AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    public void register(Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword())); // Encriptamos la contraseña antes de guardar
        usuarioRepository.save(usuario); // Guardamos el nuevo usuario en la base de datos
    }

    public String authenticate(AuthRequest authRequest) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(authRequest.getInput()); // Buscar por email
        if (usuarioOpt.isEmpty()) {
            usuarioOpt = usuarioRepository.findByUsername(authRequest.getInput()); // Si no, buscar por username
        }

        if (usuarioOpt.isEmpty()) {
            throw new RuntimeException("Credenciales inválidas"); // Si no se encuentra, lanzar error
        }

        Usuario usuario = usuarioOpt.get();

        if (!passwordEncoder.matches(authRequest.getPassword(), usuario.getPassword())) {
            throw new RuntimeException("Credenciales inválidas"); // Verificar que la contraseña coincida
        }

        return jwtUtil.generateToken(usuario.getId()); // Generar token con el ID del usuario
    }
}

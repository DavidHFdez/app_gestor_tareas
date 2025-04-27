// Controlador que gestiona el registro, login y validación de tokens

package com.gestor_tareas.usuarios_service.controller;

import com.gestor_tareas.usuarios_service.model.AuthRequest;
import com.gestor_tareas.usuarios_service.model.Usuario;
import com.gestor_tareas.usuarios_service.security.JwtUtil;
import com.gestor_tareas.usuarios_service.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UsuarioService usuarioService;
    private final JwtUtil jwtUtil;

    public AuthController(UsuarioService usuarioService, JwtUtil jwtUtil) {
        this.usuarioService = usuarioService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register") // Endpoint para registrar un nuevo usuario
    public ResponseEntity<String> register(@RequestBody Usuario usuario) {
        usuarioService.register(usuario);
        return ResponseEntity.ok("Usuario registrado con éxito");
    }

    @PostMapping("/login") // Login que devuelve un token si la autenticación es correcta
    public ResponseEntity<String> login(@RequestBody AuthRequest authRequest) {
        String token = usuarioService.authenticate(authRequest);
        return ResponseEntity.ok(token);
    }

    @GetMapping("/validate/{token}") // Verifica que el token JWT sea válido
    public ResponseEntity<Boolean> validateToken(@PathVariable String token) {
        try {
            Long userId = jwtUtil.extractUserId(token); // Extrae el userId desde el token
            boolean isValid = jwtUtil.validateToken(token, userId); // Valida el token con el userId
            return ResponseEntity.ok(isValid);
        } catch (Exception e) {
            return ResponseEntity.ok(false); // En caso de error, se considera inválido
        }
    }
}

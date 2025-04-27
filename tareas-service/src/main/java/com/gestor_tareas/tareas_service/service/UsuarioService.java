// Servicio auxiliar para acceder a los claims JWT desde otras clases

package com.gestor_tareas.tareas_service.service;

import com.gestor_tareas.tareas_service.security.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final JwtUtil jwtUtil;

    public UsuarioService(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    // Obtiene los claims decodificados del token
    public Claims obtenerClaims(String token) {
        return jwtUtil.parseToken(token);
    }
}

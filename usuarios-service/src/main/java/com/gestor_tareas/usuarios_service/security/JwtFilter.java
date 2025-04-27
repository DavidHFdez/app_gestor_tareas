// Filtro que intercepta las peticiones para validar el token JWT, excluyendo login y register

package com.gestor_tareas.usuarios_service.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    public JwtFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return path.equals("/api/auth/login") || path.equals("/api/auth/register"); // Excluimos solo login y register
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization"); // Obtenemos el header Authorization

        if (authHeader == null || !authHeader.startsWith("Bearer ")) { // Si no hay token válido, continuar sin autenticar
            System.out.println("[usuarios_service] No se recibió un token válido.");
            chain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7); // Eliminamos el prefijo "Bearer "
        System.out.println("[usuarios_service] Token recibido: " + token);

        try {
            Long userId = jwtUtil.extractUserId(token); // Extraemos el userId del token
            System.out.println("[usuarios_service] UserID extraído del token: " + userId);

            if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) { // Verificamos que no haya autenticación previa
                UserDetails userDetails = userDetailsService.loadUserByUsername(userId.toString()); // Cargamos los datos del usuario

                if (jwtUtil.validateToken(token, userId)) { // Validamos el token
                    System.out.println("[usuarios_service] Usuario autenticado correctamente: " + userId);

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()); // Creamos el token de autenticación

                    SecurityContextHolder.getContext().setAuthentication(authentication); // Asignamos al contexto de seguridad
                } else {
                    System.out.println("[usuarios_service] Token inválido.");
                }
            }
        } catch (Exception e) {
            System.out.println("[usuarios_service] Error al validar el token: " + e.getMessage());
        }

        chain.doFilter(request, response); // Continuamos con la cadena de filtros
    }
}

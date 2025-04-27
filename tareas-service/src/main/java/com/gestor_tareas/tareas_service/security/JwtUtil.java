// Clase utilitaria para validar y decodificar tokens JWT

package com.gestor_tareas.tareas_service.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    // Obtiene la clave de firma
    private Key getSigningKey() {
        if (secret == null || secret.isEmpty()) {
            throw new IllegalArgumentException("La clave JWT no puede ser null o vacía.");
        }
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Parsea el token y devuelve los claims si es válido
    public Claims parseToken(String token) {
        if (token == null || token.isEmpty()) {
            return null;
        }

        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey()) // Usa la clave para verificar
                    .build()
                    .parseClaimsJws(token) // Parsea el token
                    .getBody(); // Devuelve los claims del payload
        } catch (Exception e) {
            return null;
        }
    }

    // Extrae el userId desde el token, si está presente
    public Long getUserIdFromToken(String token) {
        Claims claims = parseToken(token);
        if (claims == null) return null;

        Object userIdObj = claims.get("userId");
        if (userIdObj == null) return null;

        try {
            return Long.valueOf(userIdObj.toString()); // Convierte el ID a Long
        } catch (NumberFormatException e) {
            return null;
        }
    }

    // Valida si el token es correcto y tiene claims
    public boolean validateToken(String token) {
        return parseToken(token) != null;
    }
}

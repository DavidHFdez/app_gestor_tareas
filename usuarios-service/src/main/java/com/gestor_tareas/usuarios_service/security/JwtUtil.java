// Clase utilitaria para la creación, extracción y validación de tokens JWT

package com.gestor_tareas.usuarios_service.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    private Key getSigningKey() {
        System.out.println("Clave utilizada en usuarios-service: " + secretKey);
        if (secretKey == null || secretKey.isEmpty()) {
            throw new IllegalArgumentException("ERROR: La clave JWT no puede ser null o vacía en usuarios-service.");
        }
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes); // Generamos la clave con el algoritmo HMAC-SHA
    }

    public String generateToken(Long userId) {
        String token = Jwts.builder()
                .claim("userId", userId) // Insertamos el userId como claim
                .setIssuedAt(new Date()) // Fecha de emisión
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // Expira en 10 horas
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // Firmamos con la clave y algoritmo
                .compact();

        System.out.println("Token generado en usuarios-service: " + token);
        System.out.println("Claims en usuarios-service: {userId=" + userId + "}");
        return token;
    }

    public Long extractUserId(String token) {
        return extractClaim(token, claims -> claims.get("userId", Long.class)); // Extraemos el userId como claim
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody(); // Parseamos el token y obtenemos los claims

            return claimsResolver.apply(claims); // Aplicamos la función para extraer el claim deseado
        } catch (Exception e) {
            System.out.println("ERROR: No se pudo extraer claims en usuarios-service: " + e.getMessage());
            return null;
        }
    }

    public boolean validateToken(String token, Long userId) {
        System.out.println("Token recibido en usuarios-service para validación: " + token);
        Long extractedUserId = extractUserId(token); // Extraemos el userId

        if (extractedUserId == null) {
            System.out.println("ERROR: No se pudo extraer userId del token en usuarios-service.");
            return false;
        }

        boolean isValid = extractedUserId.equals(userId); // Comparamos con el userId esperado
        System.out.println("Token validado en usuarios-service: " + isValid);

        return isValid;
    }
}

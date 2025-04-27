// Clase principal que arranca el servicio de usuarios

package com.gestor_tareas.usuarios_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient // Permite el registro en Eureka
@ComponentScan(basePackages = "com.gestor_tareas.usuarios_service")  // Asegura que escanea todos los paquetes
public class UsuariosServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UsuariosServiceApplication.class, args);
    }
}

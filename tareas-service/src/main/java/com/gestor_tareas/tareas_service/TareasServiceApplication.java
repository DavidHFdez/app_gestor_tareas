// Clase principal que arranca el servicio de tareas

package com.gestor_tareas.tareas_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.gestor_tareas.tareas_service.client") // Habilita clientes Feign si se usan
public class TareasServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TareasServiceApplication.class, args); // Lanza la aplicación
    }
}

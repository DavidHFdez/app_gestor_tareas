// Modelo que representa los datos necesarios para hacer login

package com.gestor_tareas.usuarios_service.model;

public class AuthRequest {
    
    private String input;  // Puede ser username, email o teléfono
    private String password;

    public AuthRequest() {}

    public AuthRequest(String input, String password) {
        this.input = input;
        this.password = password;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

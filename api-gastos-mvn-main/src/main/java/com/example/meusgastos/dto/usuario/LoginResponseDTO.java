package com.example.meusgastos.dto.usuario;

public class LoginResponseDTO {
    private String token;
    private UsuarioResponseDTO usuario;
    
    public UsuarioResponseDTO getUsuario() {
        return usuario;
    }
    public void setUsuario(UsuarioResponseDTO usuario) {
        this.usuario = usuario;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
      
}

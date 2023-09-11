package com.example.apicustom.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.apicustom.domain.model.Evento;
import com.example.apicustom.domain.model.Usuario;

public interface EventoRepository extends JpaRepository<Evento, Long> {
    List<Evento> findByUsuario(Usuario usuario);
    
}

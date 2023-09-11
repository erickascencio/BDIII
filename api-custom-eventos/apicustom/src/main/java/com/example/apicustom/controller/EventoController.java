package com.example.apicustom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.apicustom.domain.service.EventoService;
import com.example.apicustom.dto.evento.EventoRequestDTO;
import com.example.apicustom.dto.evento.EventoResponseDTO;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/eventos")
public class EventoController {
    @Autowired
    private EventoService eventoService;

    @GetMapping
    public ResponseEntity<List<EventoResponseDTO>> obterTodos(){
        return ResponseEntity.ok(eventoService.obterTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoResponseDTO> obterPorId(@PathVariable Long id){
        return ResponseEntity.ok(eventoService.obterPorId(id));
    }

    @PostMapping
    public ResponseEntity<EventoResponseDTO> cadastrar(@RequestBody EventoRequestDTO dto){
        EventoResponseDTO evento = eventoService.cadastrar(dto);
        return new ResponseEntity<>(evento, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<EventoResponseDTO> atualizar(@PathVariable Long id, @RequestBody EventoRequestDTO dto){
        EventoResponseDTO responseDTO = eventoService.atualizar(id, dto);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id){
        eventoService.deletar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

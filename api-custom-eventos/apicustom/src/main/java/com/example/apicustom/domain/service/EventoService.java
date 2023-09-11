package com.example.apicustom.domain.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.apicustom.domain.exception.ResourceNotFoundException;
import com.example.apicustom.domain.model.Evento;
import com.example.apicustom.domain.model.Usuario;
import com.example.apicustom.domain.repository.EventoRepository;
import com.example.apicustom.dto.evento.EventoRequestDTO;
import com.example.apicustom.dto.evento.EventoResponseDTO;

@Service
public class EventoService implements ICRUDService<EventoRequestDTO,EventoResponseDTO>{

@Autowired
private EventoRepository eventoRepository;
@Autowired
private ModelMapper mapper; 

    @Override
    public List<EventoResponseDTO> obterTodos() {
       Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       List<Evento> lista = eventoRepository.findByUsuario(usuario);
       return lista.stream()
       .map(evento -> mapper.map(evento, EventoResponseDTO.class))
       .collect(Collectors.toList());
    }

    @Override
    public EventoResponseDTO obterPorId(Long id) {
        Optional<Evento> optEvento = eventoRepository.findById(id);
        if(optEvento.isEmpty()){
            throw new ResourceNotFoundException("Não foi possível encontrar o evento com o id: " + id);
        }
        return mapper.map(optEvento.get(), EventoResponseDTO.class);
    }

    @Override
    public EventoResponseDTO cadastrar(EventoRequestDTO dto) {
        Evento evento = mapper.map(dto, Evento.class);
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        evento.setUsuario(usuario);
        evento.setId(null);
        evento = eventoRepository.save(evento);
        return mapper.map(evento, EventoResponseDTO.class);
    }
    
    @Override
    public EventoResponseDTO atualizar(Long id, EventoRequestDTO dto) {
       obterPorId(id);
       Evento evento = mapper.map(dto, Evento.class);
       Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       evento.setUsuario(usuario);
       evento.setId(id);
       evento = eventoRepository.save(evento);
       return mapper.map(evento, EventoResponseDTO.class);
    }

    @Override
    public void deletar(Long id) {
        obterPorId(id);
        eventoRepository.deleteById(id);
    }

}
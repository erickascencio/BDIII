package com.example.meusgastos.domain.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.meusgastos.domain.exception.ResourceBadRequestExcpetion;
import com.example.meusgastos.domain.exception.ResourceNotFoundExcpetion;
import com.example.meusgastos.domain.model.Titulo;
import com.example.meusgastos.domain.model.Usuario;
import com.example.meusgastos.domain.repository.TituloRespository;
import com.example.meusgastos.dto.titulo.TituloRequestDTO;
import com.example.meusgastos.dto.titulo.TituloResponseDTO;

@Service
public class TituloService implements ICRUDService<TituloRequestDTO, TituloResponseDTO>{
    @Autowired
    private TituloRespository tituloRespository;
    @Autowired
    private ModelMapper mapper;

    @Override
    public List<TituloResponseDTO> obterTodos() {
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("Teste" + usuario);
        List<Titulo> titulos = tituloRespository.findByUsuario(usuario);
        return titulos.stream()
        .map(titulo -> mapper.map(titulo, TituloResponseDTO.class))
        .collect(Collectors.toList());

    }

    @Override
    public TituloResponseDTO obterPorId(Long id) {
        Optional<Titulo> optTitulo = tituloRespository.findById(id);
        if(optTitulo.isEmpty()){
            throw new ResourceNotFoundExcpetion("Não foi possível encontrar o título com o id: " + id);
        }
        return mapper.map(optTitulo.get(), TituloResponseDTO.class);
    }

    @Override
    public TituloResponseDTO cadastrar(TituloRequestDTO dto) {
        validarTitulo(dto);
        Titulo titulo = mapper.map(dto,Titulo.class);
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        titulo.setUsuario(usuario);
        titulo.setId(null);
        titulo.setDataCadastro(new Date());
        titulo = tituloRespository.save(titulo);
        return mapper.map(titulo, TituloResponseDTO.class);
    }

    @Override
    public TituloResponseDTO atualizar(Long id, TituloRequestDTO dto) {
        obterPorId(id);
        validarTitulo(dto);
        Titulo titulo = mapper.map(dto, Titulo.class);
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        titulo.setUsuario(usuario);
        titulo.setId(id);
        titulo = tituloRespository.save(titulo);
        return mapper.map(titulo, TituloResponseDTO.class);
    }

    @Override
    public void deletar(Long id) {
        obterPorId(id);
        tituloRespository.deleteById(id);
    }

    public List<TituloResponseDTO> obterPorDataDeVencimento(String periodoInicial, String periodoFinal){
        List<Titulo> titulos = tituloRespository.obterFluxoCaixaPorDataVencimento(periodoInicial, periodoFinal);
        return titulos.stream()
        .map(titulo -> mapper.map(titulo, TituloResponseDTO.class))
        .collect(Collectors.toList());
    }

    private void validarTitulo(TituloRequestDTO dto){
        if(dto.getTipo() == null || dto.getDataVencimento() == null
        || dto.getValor() == null || dto.getDescricao() == null){
            throw new ResourceBadRequestExcpetion("Título Inválido - os campos são obrigatórios!");
        }
    }




 
    
}

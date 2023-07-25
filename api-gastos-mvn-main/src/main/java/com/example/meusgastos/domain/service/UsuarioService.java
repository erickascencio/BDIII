package com.example.meusgastos.domain.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.meusgastos.domain.exception.ResourceBadRequestExcpetion;
import com.example.meusgastos.domain.exception.ResourceNotFoundExcpetion;
import com.example.meusgastos.domain.model.Usuario;
import com.example.meusgastos.domain.repository.UsuarioRepository;
import com.example.meusgastos.dto.usuario.UsuarioRequestDTO;
import com.example.meusgastos.dto.usuario.UsuarioResponseDTO;

@Service
public class UsuarioService implements 
ICRUDService<UsuarioRequestDTO, UsuarioResponseDTO>{

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UsuarioResponseDTO atualizar(Long id, UsuarioRequestDTO dto) {
        UsuarioResponseDTO usuarioBanco = obterPorId(id);
        if(dto.getEmail() == null || dto.getSenha() == null){
            throw new ResourceBadRequestExcpetion("Email e Senha são Obrigatórios!");
        }
        Usuario usuario = mapper.map(dto, Usuario.class);
        //cryp senha
        usuario.setSenha(dto.getSenha());
        usuario.setId(id);
        usuario.setDataInativacao(usuarioBanco.getDataInativacao());
        usuario.setDataCadastro(usuarioBanco.getDataCadastro());
        usuario = usuarioRepository.save(usuario);
        return mapper.map(usuario, UsuarioResponseDTO.class);
    }

    @Override
    public UsuarioResponseDTO cadastrar(UsuarioRequestDTO dto) {
        if(dto.getEmail() == null || dto.getSenha() == null){
            throw new ResourceBadRequestExcpetion("Email e Senha são Obrigatórios!");
        }//criar método para não repetir código
        Optional<Usuario> optUsuario = usuarioRepository.findByEmail(dto.getEmail());
        if(optUsuario.isPresent()){
            throw new ResourceBadRequestExcpetion("Já existe um usuário cadastro com esse email: " + dto.getEmail());
        }
        Usuario usuario = mapper.map(dto, Usuario.class);
        //encriptografar senha 
        String senha = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senha);
        usuario.setId(null);
        usuario.setDataCadastro(new Date());       
        usuario = usuarioRepository.save(usuario);
        // já com o id
        return mapper.map(usuario, UsuarioResponseDTO.class);
    }

    @Override
    public void deletar(Long id) {
        Optional<Usuario> optUsuario = usuarioRepository.findById(id);
        if(optUsuario.isEmpty()){
            throw new ResourceNotFoundExcpetion("não foi possível encontrar o usuário com id:"+ id);
        }
    
      Usuario usuario = optUsuario.get();
      usuario.setDataInativacao(new Date());
      usuarioRepository.save(usuario);
    }

    @Override
    public UsuarioResponseDTO obterPorId(Long id) {
        Optional<Usuario> optUsuario = usuarioRepository.findById(id);
        if(optUsuario.isEmpty()){
            throw new ResourceNotFoundExcpetion("não foi possível encontrar o usuário com id:"+ id);
        }
        return mapper.map(optUsuario.get(), UsuarioResponseDTO.class);
     }


     public UsuarioResponseDTO obterPorEmail(String email) {
         Optional<Usuario> optUsuario = usuarioRepository.findByEmail(email);
         if(optUsuario.isEmpty()){
             throw new ResourceNotFoundExcpetion("não foi possível encontrar o usuário com email:"+ email);
         }
         return mapper.map(optUsuario.get(), UsuarioResponseDTO.class);
      }

    @Override
    public List<UsuarioResponseDTO> obterTodos() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
        .map(usuario -> mapper.map(usuario, UsuarioResponseDTO.class))
        .collect(Collectors.toList());
    }

 }
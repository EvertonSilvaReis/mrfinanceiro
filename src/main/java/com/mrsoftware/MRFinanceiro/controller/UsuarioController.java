package com.mrsoftware.MRFinanceiro.controller;

import com.mrsoftware.MRFinanceiro.dtos.usuario.UsuarioEntradaDTO;
import com.mrsoftware.MRFinanceiro.dtos.usuario.UsuarioRetornoDTO;
import com.mrsoftware.MRFinanceiro.modelo.servico.interfaces.UsuarioServico;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {

  private final UsuarioServico usuarioServico;

  @PostMapping
  public ResponseEntity<UsuarioRetornoDTO> cadastrar(
      @RequestBody @Valid UsuarioEntradaDTO usuarioEntradaDTO) {
    return new ResponseEntity(usuarioServico.cadastrar(usuarioEntradaDTO), HttpStatus.CREATED);
  }

  @PutMapping("/incluir-perfil-usuario/id/{id}")
  public ResponseEntity<UsuarioRetornoDTO> adicionarPerfilAoUsuario(
      @PathVariable("id") String id, @RequestParam @NotNull Integer tipoUsuario) {
    return new ResponseEntity(
        usuarioServico.adicionarPerfilEmUsuario(id, tipoUsuario), HttpStatus.OK);
  }
}

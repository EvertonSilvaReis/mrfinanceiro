package com.mrsoftware.MRFinanceiro.controller;

import com.mrsoftware.MRFinanceiro.dtos.usuario.UsuarioEntradaDTO;
import com.mrsoftware.MRFinanceiro.dtos.usuario.UsuarioRetornoDTO;
import com.mrsoftware.MRFinanceiro.modelo.servico.interfaces.UsuarioServico;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/usuario")
public class UsuarioController {

  @Autowired private UsuarioServico usuarioServico;

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<UsuarioRetornoDTO> cadastrar(
      @RequestBody @Valid UsuarioEntradaDTO usuarioEntradaDTO) {
    return new ResponseEntity(usuarioServico.cadastrar(usuarioEntradaDTO), HttpStatus.CREATED);
  }
}

package com.mrsoftware.MRFinanceiro.controller;

import com.mrsoftware.MRFinanceiro.dtos.autenticacao.AutenticacaoEntradaDTO;
import com.mrsoftware.MRFinanceiro.modelo.servico.interfaces.AutenticacaoServico;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("oauth2")
@CrossOrigin
@RequiredArgsConstructor
public class AutenticacaoController {

  private final AutenticacaoServico autenticacaoServico;

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody AutenticacaoEntradaDTO autenticacaoDTO) {
    return new ResponseEntity<>(autenticacaoServico.login(autenticacaoDTO), HttpStatus.OK);
  }
}

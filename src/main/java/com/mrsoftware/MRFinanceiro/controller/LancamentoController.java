package com.mrsoftware.MRFinanceiro.controller;

import com.mrsoftware.MRFinanceiro.dtos.lancamentos.LancamentoBaixaDTO;
import com.mrsoftware.MRFinanceiro.dtos.lancamentos.LancamentoEntradaDTO;
import com.mrsoftware.MRFinanceiro.dtos.lancamentos.LancamentoRetornoDTO;
import com.mrsoftware.MRFinanceiro.modelo.servico.interfaces.LancamentoServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("lancamento")
public class LancamentoController {

  @Autowired private LancamentoServico lancamentoServico;

  @PostMapping
  public ResponseEntity<LancamentoRetornoDTO> cadastrar(
      @RequestBody LancamentoEntradaDTO lancamentoEntradaDTO) {
    return new ResponseEntity<>(
        lancamentoServico.cadastrar(lancamentoEntradaDTO), HttpStatus.CREATED);
  }

  @PutMapping("id/{idLancamento}")
  public ResponseEntity<LancamentoRetornoDTO> atualizar(
      @PathVariable("idLancamento") String id,
      @RequestBody LancamentoEntradaDTO lancamentoEntradaDTO) {
    return new ResponseEntity<>(
        lancamentoServico.atualizar(id, lancamentoEntradaDTO), HttpStatus.OK);
  }

  @GetMapping("id/{idLancamento}")
  public ResponseEntity<LancamentoRetornoDTO> retornarPorId(
      @PathVariable("idLancamento") String id) {
    return new ResponseEntity<>(lancamentoServico.retornarPorId(id), HttpStatus.OK);
  }

  @DeleteMapping("id/{idLancamento}")
  public ResponseEntity<Void> excluir(@PathVariable("idLancamento") String id) {
    lancamentoServico.excluir(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PutMapping("baixar/id/{idLancamento}")
  public ResponseEntity<LancamentoRetornoDTO> baixarTitulo(
      @PathVariable("idLancamento") String id, @RequestBody LancamentoBaixaDTO lancamentoBaixaDTO) {
    return new ResponseEntity<>(
        lancamentoServico.baixarTitulo(id, lancamentoBaixaDTO), HttpStatus.OK);
  }
}

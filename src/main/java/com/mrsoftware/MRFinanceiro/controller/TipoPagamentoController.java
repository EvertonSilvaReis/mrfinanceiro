package com.mrsoftware.MRFinanceiro.controller;

import com.mrsoftware.MRFinanceiro.dtos.tipoPagamento.TipoPagamentoEntradaDTO;
import com.mrsoftware.MRFinanceiro.dtos.tipoPagamento.TipoPagamentoEntradaPaginadaDTO;
import com.mrsoftware.MRFinanceiro.dtos.tipoPagamento.TipoPagamentoRetornoDTO;
import com.mrsoftware.MRFinanceiro.dtos.tipoPagamento.TipoPagamentoRetornoPaginadoDTO;
import com.mrsoftware.MRFinanceiro.modelo.servico.interfaces.TipoPagamentoServico;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("tipo-pagamento")
public class TipoPagamentoController {

  @Autowired private TipoPagamentoServico tipoPagamentoServico;

  @PostMapping
  public ResponseEntity<TipoPagamentoRetornoDTO> cadastrar(
      @RequestBody @Valid TipoPagamentoEntradaDTO tipoPagamentoEntradaDTO) {
    return new ResponseEntity<>(
        tipoPagamentoServico.cadastrar(tipoPagamentoEntradaDTO), HttpStatus.CREATED);
  }

  @PutMapping("id/{id}")
  public ResponseEntity<TipoPagamentoRetornoDTO> atualizar(
      @PathVariable String id,
      @RequestBody @Valid TipoPagamentoEntradaDTO tipoPagamentoEntradaDTO) {
    return new ResponseEntity<>(
        tipoPagamentoServico.atualizar(id, tipoPagamentoEntradaDTO), HttpStatus.OK);
  }

  @GetMapping("id/{id}")
  public ResponseEntity<TipoPagamentoRetornoDTO> retornarPorId(@PathVariable String id) {
    return new ResponseEntity<>(tipoPagamentoServico.retornarTipoPagamentoPorId(id), HttpStatus.OK);
  }

  @GetMapping("/filtro")
  public ResponseEntity<TipoPagamentoRetornoPaginadoDTO> retornarPorFiltro(
      TipoPagamentoEntradaPaginadaDTO tipoPagamentoEntradaPaginadaDTO) {
    return new ResponseEntity<>(
        tipoPagamentoServico.retornarPorFiltro(tipoPagamentoEntradaPaginadaDTO), HttpStatus.OK);
  }

  @DeleteMapping("id/{id}")
  public ResponseEntity<Void> excluir(@PathVariable String id) {
    tipoPagamentoServico.excluir(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}

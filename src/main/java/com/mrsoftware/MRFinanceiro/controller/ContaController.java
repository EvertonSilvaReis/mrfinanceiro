package com.mrsoftware.MRFinanceiro.controller;

import com.mrsoftware.MRFinanceiro.dtos.conta.ContaEntradaDTO;
import com.mrsoftware.MRFinanceiro.dtos.conta.ContaEntradaPaginadaDTO;
import com.mrsoftware.MRFinanceiro.dtos.conta.ContaRetornoDTO;
import com.mrsoftware.MRFinanceiro.dtos.conta.ContaRetornoPaginadoDTO;
import com.mrsoftware.MRFinanceiro.modelo.servico.interfaces.ContaServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("conta")
public class ContaController {

  @Autowired private ContaServico contaServico;

  @PostMapping
  public ResponseEntity<ContaRetornoDTO> cadastrar(@RequestBody ContaEntradaDTO contaEntradaDTO) {
    return new ResponseEntity<>(contaServico.cadastrar(contaEntradaDTO), HttpStatus.CREATED);
  }

  @PutMapping("id/{idConta}")
  public ResponseEntity<ContaRetornoDTO> atualizar(
      @PathVariable("idConta") String id, @RequestBody ContaEntradaDTO contaEntradaDTO) {
    return new ResponseEntity<>(contaServico.atualizar(id, contaEntradaDTO), HttpStatus.OK);
  }

  @GetMapping("id/{idConta}")
  public ResponseEntity<ContaRetornoDTO> retornarPorId(@PathVariable("idConta") String id) {
    return new ResponseEntity<>(contaServico.retornarContaPorId(id), HttpStatus.OK);
  }

  @GetMapping("filtro")
  public ResponseEntity<ContaRetornoPaginadoDTO> retornarPorFiltro(
      ContaEntradaPaginadaDTO contaEntradaDTO) {
    return new ResponseEntity<>(contaServico.retornarPorFiltro(contaEntradaDTO), HttpStatus.OK);
  }

  @DeleteMapping("id/{idConta}")
  public ResponseEntity<Void> excluir(@PathVariable("idConta") String id) {
    contaServico.excluir(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}

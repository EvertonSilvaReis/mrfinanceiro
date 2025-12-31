package com.mrsoftware.MRFinanceiro.controller;

import com.mrsoftware.MRFinanceiro.dtos.pessoa.PessoaEntradaDTO;
import com.mrsoftware.MRFinanceiro.dtos.pessoa.PessoaEntradaPaginadaDTO;
import com.mrsoftware.MRFinanceiro.dtos.pessoa.PessoaRetornoDTO;
import com.mrsoftware.MRFinanceiro.dtos.pessoa.PessoaRetornoPaginadoDTO;
import com.mrsoftware.MRFinanceiro.modelo.servico.interfaces.PessoaServico;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("pessoa")
@RequiredArgsConstructor
public class PessoaController {

  private final PessoaServico pessoaServico;

  @PostMapping
  public ResponseEntity<PessoaRetornoDTO> cadastrar(
      @RequestBody @Valid PessoaEntradaDTO pessoaEntradaDTO) {
    return new ResponseEntity<>(pessoaServico.cadastrar(pessoaEntradaDTO), HttpStatus.CREATED);
  }

  @PutMapping("id/{idPessoa}")
  public ResponseEntity<PessoaRetornoDTO> atualizar(
      @PathVariable("idPessoa") String idPessoa,
      @RequestBody @Valid PessoaEntradaDTO pessoaEntradaDTO) {
    return new ResponseEntity<>(pessoaServico.atualizar(idPessoa, pessoaEntradaDTO), HttpStatus.OK);
  }

  @GetMapping("id/{idPessoa}")
  public ResponseEntity<PessoaRetornoDTO> retornarPorId(@PathVariable("idPessoa") String idPessoa) {
    return new ResponseEntity<>(pessoaServico.retornarPessoaPorId(idPessoa), HttpStatus.OK);
  }

  @GetMapping("/filtro")
  public ResponseEntity<PessoaRetornoPaginadoDTO> retornarPorFiltro(
      PessoaEntradaPaginadaDTO pessoaEntradaPaginadaDTO) {
    return new ResponseEntity<>(
        pessoaServico.retornarPorFiltro(pessoaEntradaPaginadaDTO), HttpStatus.OK);
  }

  @DeleteMapping("id/{idPessoa}")
  public ResponseEntity<Void> excluir(@PathVariable("idPessoa") String idPessoa) {
    pessoaServico.excluir(idPessoa);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}

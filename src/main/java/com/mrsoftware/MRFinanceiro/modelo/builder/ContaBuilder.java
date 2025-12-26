package com.mrsoftware.MRFinanceiro.modelo.builder;

import com.mrsoftware.MRFinanceiro.dtos.conta.ContaEntradaDTO;
import com.mrsoftware.MRFinanceiro.dtos.conta.ContaRetornoDTO;
import com.mrsoftware.MRFinanceiro.modelo.entidade.Conta;
import java.util.Objects;
import org.springframework.stereotype.Component;

@Component
public class ContaBuilder {
  private ContaEntradaDTO contaEntradaDTO;
  private Conta conta;

  public ContaBuilder addContaEntradaDTO(ContaEntradaDTO contaEntradaDTO) {
    this.contaEntradaDTO = contaEntradaDTO;
    return this;
  }

  public ContaBuilder addConta(Conta conta) {
    this.conta = conta;
    return this;
  }

  public Conta buildDtoParaEntidade() {
    return Conta.builder()
        .descricao(contaEntradaDTO.getDescricao())
        .numeroConta(contaEntradaDTO.getNumeroConta())
        .agencia(contaEntradaDTO.getAgencia())
        .banco(contaEntradaDTO.getBanco())
        .saldoInicial(contaEntradaDTO.getSaldoInicial())
        .saldoAtual(contaEntradaDTO.getSaldoAtual())
        .ativo(true)
        .build();
  }

  public ContaRetornoDTO buildEntidadeParaDto() {
    return ContaRetornoDTO.builder()
        .id(conta.getId())
        .descricao(conta.getDescricao())
        .numeroConta(conta.getNumeroConta())
        .agencia(conta.getAgencia())
        .banco(conta.getBanco())
        .saldoInicial(conta.getSaldoInicial())
        .saldoAtual(conta.getSaldoAtual())
        .ativo(conta.getAtivo())
        .build();
  }

  public Conta buildAtualizarConta() {
    return Conta.builder()
        .descricao(
            Objects.nonNull(contaEntradaDTO.getDescricao())
                ? contaEntradaDTO.getDescricao()
                : conta.getDescricao())
        .numeroConta(
            Objects.nonNull(contaEntradaDTO.getNumeroConta())
                ? contaEntradaDTO.getNumeroConta()
                : conta.getNumeroConta())
        .agencia(
            Objects.nonNull(contaEntradaDTO.getAgencia())
                ? contaEntradaDTO.getAgencia()
                : conta.getAgencia())
        .banco(
            Objects.nonNull(contaEntradaDTO.getBanco())
                ? contaEntradaDTO.getBanco()
                : conta.getBanco())
        .saldoInicial(
            Objects.nonNull(contaEntradaDTO.getSaldoInicial())
                ? contaEntradaDTO.getSaldoInicial()
                : conta.getSaldoInicial())
        .saldoAtual(
            Objects.nonNull(contaEntradaDTO.getSaldoAtual())
                ? contaEntradaDTO.getSaldoAtual()
                : conta.getSaldoAtual())
        .ativo(true)
        .build();
  }
}

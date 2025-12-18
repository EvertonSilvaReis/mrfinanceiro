package com.mrsoftware.MRFinanceiro.dtos.conta;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContaEntradaDTO {
  private String descricao;
  private Integer numeroConta;
  private Integer agencia;
  private BigDecimal saldoInicial;
  private BigDecimal saldoAtual;
}

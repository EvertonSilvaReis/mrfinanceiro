package com.mrsoftware.MRFinanceiro.dtos.conta;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContaRetornoDTO {
  private UUID id;
  private String descricao;
  private Integer numeroConta;
  private Integer agencia;
  private BigDecimal saldoInicial;
  private BigDecimal saldoAtual;
  private Boolean ativo;
}

package com.mrsoftware.MRFinanceiro.dtos.conta;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ContaRetornoDTO {
  private UUID id;
  private String descricao;
  private Integer numeroConta;
  private Integer agencia;
  private Integer banco;
  private BigDecimal saldoInicial;
  private BigDecimal saldoAtual;
  private Boolean ativo;
}

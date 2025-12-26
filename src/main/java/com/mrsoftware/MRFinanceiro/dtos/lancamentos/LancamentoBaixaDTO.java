package com.mrsoftware.MRFinanceiro.dtos.lancamentos;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class LancamentoBaixaDTO {
  private String id;
  private String dataPagamento;
  private BigDecimal valorPagamento;
}

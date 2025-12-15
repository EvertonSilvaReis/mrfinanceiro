package com.mrsoftware.MRFinanceiro.dtos.tipoPagamento;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TipoPagamentoRetornoDTO {
  private UUID id;
  private String descricao;
  private Boolean parcelado;
  private Integer parcelas;
}

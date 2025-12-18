package com.mrsoftware.MRFinanceiro.dtos.tipoPagamento;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TipoPagamentoRetornoDTO {
  private UUID id;
  private String descricao;
  private Boolean parcelado;
  private Integer parcelas;
  private Boolean ativo;
}

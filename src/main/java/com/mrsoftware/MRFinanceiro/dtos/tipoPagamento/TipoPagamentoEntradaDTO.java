package com.mrsoftware.MRFinanceiro.dtos.tipoPagamento;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TipoPagamentoEntradaDTO {
  @NotBlank private String descricao;
  @NotBlank private Boolean parcelado;
  private Integer parcelas = 1;
}

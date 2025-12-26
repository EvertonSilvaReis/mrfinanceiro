package com.mrsoftware.MRFinanceiro.dtos.tipoPagamento;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TipoPagamentoEntradaDTO {
  @NotBlank private String descricao;
  @NotNull private Boolean parcelado;
  private Integer parcelas;
}

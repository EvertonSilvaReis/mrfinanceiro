package com.mrsoftware.MRFinanceiro.dtos.erros;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ErroDTO {
  private Integer codigo;
  private String mensagem;
}

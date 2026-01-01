package com.mrsoftware.MRFinanceiro.dtos.autenticacao;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AutenticacaoRetornoDTO {
  private String token;
  private String usuario;
}

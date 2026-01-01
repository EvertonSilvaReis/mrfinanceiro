package com.mrsoftware.MRFinanceiro.dtos.autenticacao;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AutenticacaoEntradaDTO {
  @NotBlank private String email;
  @NotBlank private String senha;
}

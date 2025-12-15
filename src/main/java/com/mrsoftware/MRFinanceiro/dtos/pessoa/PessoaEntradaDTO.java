package com.mrsoftware.MRFinanceiro.dtos.pessoa;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PessoaEntradaDTO {
  @NotBlank private String nome;
  private String cpfCnpj;
  @NotNull private Integer tipoPessoa;
}

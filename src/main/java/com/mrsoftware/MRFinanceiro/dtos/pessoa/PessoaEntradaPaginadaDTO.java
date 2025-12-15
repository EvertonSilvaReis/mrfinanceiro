package com.mrsoftware.MRFinanceiro.dtos.pessoa;

import com.mrsoftware.MRFinanceiro.dtos.PaginacaoFiltroDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PessoaEntradaPaginadaDTO extends PaginacaoFiltroDTO {
  private String nome;
  private String cpfCnpj;
}

package com.mrsoftware.MRFinanceiro.dtos.conta;

import com.mrsoftware.MRFinanceiro.dtos.PaginacaoFiltroDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContaEntradaPaginadaDTO extends PaginacaoFiltroDTO {
  private String descricao;
  private String banco;
}

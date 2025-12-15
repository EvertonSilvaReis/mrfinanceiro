package com.mrsoftware.MRFinanceiro.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaginacaoFiltroDTO {
  private Integer pagina = 0;
  private Integer itensPorPagina = 10;
}

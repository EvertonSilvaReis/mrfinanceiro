package com.mrsoftware.MRFinanceiro.dtos.conta;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ContaRetornoPaginadoDTO {

  private Integer itensPorPagina;
  private Integer pagina;
  private List<ContaRetornoDTO> conta;
}

package com.mrsoftware.MRFinanceiro.dtos.pessoa;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PessoaRetornoPaginadoDTO {

  private Integer itensPorPagina;
  private Integer pagina;
  private List<PessoaRetornoDTO> pessoas;
}

package com.mrsoftware.MRFinanceiro.dtos.tipoPagamento;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TipoPagamentoRetornoPaginadoDTO {

  private Integer itensPorPagina;
  private Integer pagina;
  private List<TipoPagamentoRetornoDTO> tipoPagamento;
}

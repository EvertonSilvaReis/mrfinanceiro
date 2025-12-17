package com.mrsoftware.MRFinanceiro.dtos.tipoPagamento;

import com.mrsoftware.MRFinanceiro.dtos.PaginacaoFiltroDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TipoPagamentoEntradaPaginadaDTO extends PaginacaoFiltroDTO {
  private String descricao;
}

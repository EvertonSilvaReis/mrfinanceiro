package com.mrsoftware.MRFinanceiro.dtos.lancamentos;

import com.mrsoftware.MRFinanceiro.dtos.pessoa.PessoaEntradaDTO;
import com.mrsoftware.MRFinanceiro.dtos.tipoPagamento.TipoPagamentoEntradaDTO;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LancamentoEntradaDTO {
  private String descricao;
  private Integer codigo;
  private String numeroDocumento;
  private Integer tipoLancamento;
  private BigDecimal valorTitulo;
  private BigDecimal desconto;
  private String dataEmissao;
  private String dataVencimento;
  private BigDecimal valorPagamento;
  private String dataPagamento;
  private String observacao;
  private TipoPagamentoEntradaDTO tipoPagamento;
  private Integer parcela;
  private PessoaEntradaDTO pessoa;
}

package com.mrsoftware.MRFinanceiro.dtos.lancamentos;

import com.mrsoftware.MRFinanceiro.dtos.conta.ContaRetornoDTO;
import com.mrsoftware.MRFinanceiro.dtos.pessoa.PessoaRetornoDTO;
import com.mrsoftware.MRFinanceiro.dtos.tipoPagamento.TipoPagamentoRetornoDTO;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class LancamentoRetornoDTO {
  private UUID id;
  private String descricao;
  private String codigo;
  private String numeroDocumento;
  private String tipoLancamento;
  private BigDecimal valorTitulo;
  private BigDecimal desconto;
  private LocalDate dataEmissao;
  private LocalDate dataVencimento;
  private BigDecimal valorPagamento;
  private LocalDate dataPagamento;
  private String situacao;
  private String observacao;
  private TipoPagamentoRetornoDTO tipoPagamento;
  private Integer parcela;
  private PessoaRetornoDTO pessoa;
  private ContaRetornoDTO conta;
}

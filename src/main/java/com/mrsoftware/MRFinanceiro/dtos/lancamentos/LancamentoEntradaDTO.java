package com.mrsoftware.MRFinanceiro.dtos.lancamentos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LancamentoEntradaDTO {
  @NotBlank private String descricao;

  @NotBlank private String numeroDocumento;

  @NotNull private Integer tipoLancamento;

  @NotNull private BigDecimal valorTitulo;

  private BigDecimal desconto;

  @NotBlank private String dataEmissao;

  @NotBlank private String dataVencimento;

  private BigDecimal valorPagamento;
  private String dataPagamento;
  private String observacao;

  @NotNull @Valid private UUID tipoPagamento;

  @NotNull private UUID pessoa;

  @NotNull private UUID conta;
}

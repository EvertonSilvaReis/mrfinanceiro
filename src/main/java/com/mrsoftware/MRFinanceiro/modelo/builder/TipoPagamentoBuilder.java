package com.mrsoftware.MRFinanceiro.modelo.builder;

import com.mrsoftware.MRFinanceiro.dtos.tipoPagamento.TipoPagamentoEntradaDTO;
import com.mrsoftware.MRFinanceiro.dtos.tipoPagamento.TipoPagamentoRetornoDTO;
import com.mrsoftware.MRFinanceiro.modelo.entidade.TipoPagamento;
import java.util.Objects;
import org.springframework.stereotype.Component;

@Component
public class TipoPagamentoBuilder {
  private TipoPagamentoEntradaDTO tipoPagamentoEntradaDTO;
  private TipoPagamento tipoPagamento;

  public TipoPagamentoBuilder addTipoPagamentoEntradaDTO(
      TipoPagamentoEntradaDTO tipoPagamentoEntradaDTO) {
    this.tipoPagamentoEntradaDTO = tipoPagamentoEntradaDTO;
    return this;
  }

  public TipoPagamentoBuilder addTipoPagamento(TipoPagamento tipoPagamento) {
    this.tipoPagamento = tipoPagamento;
    return this;
  }

  public TipoPagamento buildCadastrarTipoPagamento() {
    return TipoPagamento.builder()
        .descricao(tipoPagamentoEntradaDTO.getDescricao())
        .parcelado(tipoPagamentoEntradaDTO.getParcelado())
        .parcelas(
            tipoPagamentoEntradaDTO.getParcelado()
                    && Objects.nonNull(tipoPagamentoEntradaDTO.getParcelas())
                ? tipoPagamentoEntradaDTO.getParcelas()
                : 1)
        .ativo(true)
        .build();
  }

  public TipoPagamentoRetornoDTO buildRetornoTipoPagamento() {
    return TipoPagamentoRetornoDTO.builder()
        .id(tipoPagamento.getId())
        .descricao(tipoPagamento.getDescricao())
        .parcelado(tipoPagamento.getParcelado())
        .parcelas(tipoPagamento.getParcelas())
        .ativo(tipoPagamento.getAtivo())
        .build();
  }

  public TipoPagamento buildAtualizarPessoa() {
    return TipoPagamento.builder()
        .id(tipoPagamento.getId())
        .descricao(
            tipoPagamentoEntradaDTO.getDescricao() != null
                ? tipoPagamentoEntradaDTO.getDescricao()
                : tipoPagamento.getDescricao())
        .parcelado(
            tipoPagamentoEntradaDTO.getParcelado() != null
                ? tipoPagamentoEntradaDTO.getParcelado()
                : tipoPagamento.getParcelado())
        .parcelas(
            tipoPagamentoEntradaDTO.getParcelado() && tipoPagamentoEntradaDTO.getParcelas() != null
                ? tipoPagamentoEntradaDTO.getParcelas()
                : tipoPagamento.getParcelas())
        .build();
  }
}

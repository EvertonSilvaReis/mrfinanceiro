package com.mrsoftware.MRFinanceiro.modelo.servico.interfaces;

import com.mrsoftware.MRFinanceiro.dtos.tipoPagamento.TipoPagamentoEntradaDTO;
import com.mrsoftware.MRFinanceiro.dtos.tipoPagamento.TipoPagamentoEntradaPaginadaDTO;
import com.mrsoftware.MRFinanceiro.dtos.tipoPagamento.TipoPagamentoRetornoDTO;
import com.mrsoftware.MRFinanceiro.dtos.tipoPagamento.TipoPagamentoRetornoPaginadoDTO;

public interface TipoPagamentoServico {
  TipoPagamentoRetornoDTO cadastrar(TipoPagamentoEntradaDTO pessoaEntradaDTO);

  TipoPagamentoRetornoDTO atualizar(String id, TipoPagamentoEntradaDTO pessoaEntradaDTO);

  TipoPagamentoRetornoDTO retornarPessoaPorId(String id);

  TipoPagamentoRetornoPaginadoDTO retornarPorFiltro(
      TipoPagamentoEntradaPaginadaDTO pessoaEntradaPaginadaDTO);

  void excluir(String id);
}

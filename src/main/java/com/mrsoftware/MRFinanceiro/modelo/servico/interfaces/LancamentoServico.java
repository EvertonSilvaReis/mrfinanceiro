package com.mrsoftware.MRFinanceiro.modelo.servico.interfaces;

import com.mrsoftware.MRFinanceiro.dtos.lancamentos.LancamentoBaixaDTO;
import com.mrsoftware.MRFinanceiro.dtos.lancamentos.LancamentoEntradaDTO;
import com.mrsoftware.MRFinanceiro.dtos.lancamentos.LancamentoRetornoDTO;

public interface LancamentoServico {

  LancamentoRetornoDTO cadastrar(LancamentoEntradaDTO lancamentoEntradaDTO);

  LancamentoRetornoDTO atualizar(String id, LancamentoEntradaDTO lancamentoEntradaDTO);

  LancamentoRetornoDTO retornarPorId(String id);

  void excluir(String id);

  LancamentoRetornoDTO baixarTitulo(String id, LancamentoBaixaDTO lancamentoBaixaDTO);
}

package com.mrsoftware.MRFinanceiro.modelo.servico.interfaces;

import com.mrsoftware.MRFinanceiro.dtos.lancamentos.LancamentoEntradaDTO;
import com.mrsoftware.MRFinanceiro.dtos.lancamentos.LancamentoRetornoDTO;

public interface LancamentoServico {

  LancamentoRetornoDTO cadastrar(LancamentoEntradaDTO lancamentoEntradaDTO);
}

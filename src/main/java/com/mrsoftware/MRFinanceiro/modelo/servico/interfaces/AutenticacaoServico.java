package com.mrsoftware.MRFinanceiro.modelo.servico.interfaces;

import com.mrsoftware.MRFinanceiro.dtos.autenticacao.AutenticacaoEntradaDTO;
import com.mrsoftware.MRFinanceiro.dtos.autenticacao.AutenticacaoRetornoDTO;

public interface AutenticacaoServico {

  AutenticacaoRetornoDTO login(AutenticacaoEntradaDTO autenticacaoEntradaDTO);
}

package com.mrsoftware.MRFinanceiro.modelo.servico.interfaces;

import com.mrsoftware.MRFinanceiro.dtos.pessoa.PessoaEntradaDTO;
import com.mrsoftware.MRFinanceiro.dtos.pessoa.PessoaEntradaPaginadaDTO;
import com.mrsoftware.MRFinanceiro.dtos.pessoa.PessoaRetornoDTO;
import com.mrsoftware.MRFinanceiro.dtos.pessoa.PessoaRetornoPaginadoDTO;

public interface PessoaServico {
  PessoaRetornoDTO cadastrar(PessoaEntradaDTO pessoaEntradaDTO);

  PessoaRetornoDTO atualizar(String id, PessoaEntradaDTO pessoaEntradaDTO);

  PessoaRetornoDTO retornarPessoaPorId(String id);

  PessoaRetornoPaginadoDTO retornarPorFiltro(PessoaEntradaPaginadaDTO pessoaEntradaPaginadaDTO);
}

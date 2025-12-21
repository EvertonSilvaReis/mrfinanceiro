package com.mrsoftware.MRFinanceiro.modelo.servico.interfaces;

import com.mrsoftware.MRFinanceiro.dtos.conta.ContaEntradaDTO;
import com.mrsoftware.MRFinanceiro.dtos.conta.ContaEntradaPaginadaDTO;
import com.mrsoftware.MRFinanceiro.dtos.conta.ContaRetornoDTO;
import com.mrsoftware.MRFinanceiro.dtos.conta.ContaRetornoPaginadoDTO;
import com.mrsoftware.MRFinanceiro.modelo.entidade.Conta;
import java.util.UUID;

public interface ContaServico {

  ContaRetornoDTO cadastrar(ContaEntradaDTO contaEntradaDTO);

  ContaRetornoDTO atualizar(String id, ContaEntradaDTO contaEntradaDTO);

  ContaRetornoDTO retornarContaPorId(String id);

  ContaRetornoPaginadoDTO retornarPorFiltro(ContaEntradaPaginadaDTO contaEntradaPaginadaDTO);

  void excluir(String id);

  Conta obterContaPorId(UUID id);
}

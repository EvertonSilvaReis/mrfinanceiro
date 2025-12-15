package com.mrsoftware.MRFinanceiro.modelo.servico;

import com.mrsoftware.MRFinanceiro.modelo.repositorios.ConfiguracaoRepositorio;
import com.mrsoftware.MRFinanceiro.modelo.servico.interfaces.ConfiguracaoServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfiguracaoServicoImpl implements ConfiguracaoServico {

  @Autowired private ConfiguracaoRepositorio configuracaoRepositorio;

  @Override
  public Integer obterUltimoCodigo(String descricao) {
    return Integer.parseInt(configuracaoRepositorio.findByDescricao(descricao).get().getValor());
  }
}

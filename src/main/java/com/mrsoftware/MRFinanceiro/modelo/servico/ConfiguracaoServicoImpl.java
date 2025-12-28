package com.mrsoftware.MRFinanceiro.modelo.servico;

import com.mrsoftware.MRFinanceiro.modelo.entidade.Configuracao;
import com.mrsoftware.MRFinanceiro.modelo.repositorios.ConfiguracaoRepositorio;
import com.mrsoftware.MRFinanceiro.modelo.servico.interfaces.ConfiguracaoServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfiguracaoServicoImpl implements ConfiguracaoServico {

  @Autowired private ConfiguracaoRepositorio configuracaoRepositorio;

  @Override
  public Integer obterCodigo(String descricao) {
    Configuracao configuracao = configuracaoRepositorio.findByDescricao(descricao).orElseThrow();
    int ultimoCodigo = Integer.parseInt(configuracao.getValor());
    configuracao.setValor(String.valueOf(ultimoCodigo + 1));
    return ultimoCodigo + 1;
  }
}

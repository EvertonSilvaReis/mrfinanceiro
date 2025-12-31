package com.mrsoftware.MRFinanceiro.modelo.servico;

import com.mrsoftware.MRFinanceiro.modelo.entidade.Configuracao;
import com.mrsoftware.MRFinanceiro.modelo.repositorios.ConfiguracaoRepositorio;
import com.mrsoftware.MRFinanceiro.modelo.servico.interfaces.ConfiguracaoServico;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConfiguracaoServicoImpl implements ConfiguracaoServico {

  private final ConfiguracaoRepositorio configuracaoRepositorio;

  @Override
  public Integer obterCodigo(String descricao) {
    Configuracao configuracao = configuracaoRepositorio.findByDescricao(descricao).orElseThrow();
    int ultimoCodigo = Integer.parseInt(configuracao.getValor());
    configuracao.setValor(String.valueOf(ultimoCodigo + 1));
    return ultimoCodigo + 1;
  }
}

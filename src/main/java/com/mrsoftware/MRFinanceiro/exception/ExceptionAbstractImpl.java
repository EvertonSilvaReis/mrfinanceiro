package com.mrsoftware.MRFinanceiro.exception;

import com.mrsoftware.MRFinanceiro.modelo.enumeradores.EValidacao;
import lombok.Getter;

public class ExceptionAbstractImpl extends RuntimeException implements ExceptionAbstract {

  private EValidacao validacao;
  @Getter private String[] params;

  @Override
  public Integer codigo() {
    return this.validacao.getCodigo();
  }

  @Override
  public String getMensagem() {
    return this.validacao.getDescricao(params);
  }

  public ExceptionAbstractImpl(EValidacao validacao, String... params) {
    this.validacao = validacao;
    this.params = params;
  }
}

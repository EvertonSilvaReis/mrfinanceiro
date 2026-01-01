package com.mrsoftware.MRFinanceiro.exception;

import com.mrsoftware.MRFinanceiro.modelo.enumeradores.EValidacao;

public class UnauthorizedException extends ExceptionAbstractImpl {

  public UnauthorizedException(EValidacao validacao, String... msgm) {
    super(validacao, msgm);
  }
}

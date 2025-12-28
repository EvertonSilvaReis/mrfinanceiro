package com.mrsoftware.MRFinanceiro.exception;

import com.mrsoftware.MRFinanceiro.modelo.enumeradores.EValidacao;

public class ForbiddenException extends ExceptionAbstractImpl {

  public ForbiddenException(EValidacao validacao, String... msgm) {
    super(validacao, msgm);
  }
}

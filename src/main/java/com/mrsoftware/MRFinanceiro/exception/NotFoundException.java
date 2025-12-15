package com.mrsoftware.MRFinanceiro.exception;

import com.mrsoftware.MRFinanceiro.modelo.enumeradores.EValidacao;

public class NotFoundException extends ExceptionAbstractImpl {

  public NotFoundException(EValidacao validacao, String... msgm) {
    super(validacao, msgm);
  }
}

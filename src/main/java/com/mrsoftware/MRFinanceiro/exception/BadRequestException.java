package com.mrsoftware.MRFinanceiro.exception;

import com.mrsoftware.MRFinanceiro.modelo.enumeradores.EValidacao;

public class BadRequestException extends ExceptionAbstractImpl {

  public BadRequestException(EValidacao validacao, String... msgm) {
    super(validacao, msgm);
  }
}

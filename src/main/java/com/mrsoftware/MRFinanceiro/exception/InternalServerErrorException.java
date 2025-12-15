package com.mrsoftware.MRFinanceiro.exception;

import com.mrsoftware.MRFinanceiro.modelo.enumeradores.EValidacao;

public class InternalServerErrorException extends ExceptionAbstractImpl {
  public InternalServerErrorException(EValidacao validacao, String... params) {
    super(validacao, params);
  }
}

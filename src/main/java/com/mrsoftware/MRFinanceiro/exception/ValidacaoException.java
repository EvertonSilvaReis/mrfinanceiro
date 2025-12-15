package com.mrsoftware.MRFinanceiro.exception;

import com.mrsoftware.MRFinanceiro.modelo.enumeradores.EValidacao;

public class ValidacaoException extends ExceptionAbstractImpl {

  public ValidacaoException(EValidacao validacao, String... params) {
    super(validacao, params);
  }
}

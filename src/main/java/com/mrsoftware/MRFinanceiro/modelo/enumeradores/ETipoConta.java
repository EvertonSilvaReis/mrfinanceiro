package com.mrsoftware.MRFinanceiro.modelo.enumeradores;

import com.mrsoftware.MRFinanceiro.exception.NotFoundException;
import java.util.Objects;
import lombok.Getter;

@Getter
public enum ETipoConta {
  POUPANCA(0),
  CORRENTE(1);

  private int codigo;

  ETipoConta(Integer codigo) {
    this.codigo = codigo;
  }

  public static ETipoConta obterTipoPessoa(Object entrada) {
    if (Objects.isNull(entrada)) return null;

    for (ETipoConta codigoPessoa : ETipoConta.values()) {
      if (entrada.equals(codigoPessoa.codigo)) {
        return codigoPessoa;
      }
    }

    throw new NotFoundException(EValidacao.TIPO_CONTA_NAO_ENCONTRADO, entrada.toString());
  }
}

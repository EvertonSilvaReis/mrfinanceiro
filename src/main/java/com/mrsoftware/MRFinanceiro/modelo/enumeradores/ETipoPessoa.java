package com.mrsoftware.MRFinanceiro.modelo.enumeradores;

import com.mrsoftware.MRFinanceiro.exception.NotFoundException;
import java.util.Objects;
import lombok.Getter;

@Getter
public enum ETipoPessoa {
  FISICA("FISICA", 0),
  JURIDICA("JURIDICA", 1);

  private String tipo;
  private int codigo;

  ETipoPessoa(String tipo, Integer codigo) {
    this.tipo = tipo;
    this.codigo = codigo;
  }

  public static ETipoPessoa obterTipoPessoa(Object entrada) {
    if (Objects.isNull(entrada)) return null;

    for (ETipoPessoa codigoPessoa : ETipoPessoa.values()) {
      if (entrada.equals(codigoPessoa.codigo) || entrada.equals(codigoPessoa.tipo)) {
        return codigoPessoa;
      }
    }

    throw new NotFoundException(EValidacao.TIPO_PESSOA_NAO_ENCONTRADO, entrada.toString());
  }
}

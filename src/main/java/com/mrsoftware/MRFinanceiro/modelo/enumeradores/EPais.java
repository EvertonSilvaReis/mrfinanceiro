package com.mrsoftware.MRFinanceiro.modelo.enumeradores;

import com.mrsoftware.MRFinanceiro.exception.NotFoundException;
import java.util.Objects;
import lombok.Getter;

@Getter
public enum EPais {
  BRASIL("BR", 0);

  private String sigla;
  private Integer codigo;

  EPais(String sigla, Integer codigo) {
    this.sigla = sigla;
    this.codigo = codigo;
  }

  public EPais getPaisPorSigla(String sigla) {
    return obterEPais(sigla);
  }

  public EPais getPaisPorCodigo(Integer codigo) {
    return obterEPais(codigo);
  }

  private EPais obterEPais(Object tipo) {
    if (Objects.isNull(tipo)) return null;

    for (EPais paisLancamento : EPais.values()) {
      if (tipo.equals(paisLancamento.codigo) || tipo.equals(paisLancamento.sigla)) {
        return paisLancamento;
      }
    }

    throw new NotFoundException(EValidacao.PAIS_NAO_ENCONTRADO, tipo.toString());
  }
}

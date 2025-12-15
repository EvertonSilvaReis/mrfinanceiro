package com.mrsoftware.MRFinanceiro.modelo.enumeradores;

import com.mrsoftware.MRFinanceiro.exception.NotFoundException;
import java.util.Objects;
import lombok.Getter;

@Getter
public enum EEstado {
  BAHIA("BA", 0);

  private String sigla;
  private Integer codigo;

  EEstado(String sigla, Integer codigo) {
    this.sigla = sigla;
    this.codigo = codigo;
  }

  public EEstado getEstadoPorSigla(String sigla) {
    return obterEEstado(sigla);
  }

  public EEstado getEstadoPorCodigo(Integer codigo) {
    return obterEEstado(codigo);
  }

  private EEstado obterEEstado(Object tipo) {
    if (Objects.isNull(tipo)) return null;

    for (EEstado estadoLancamento : EEstado.values()) {
      if (tipo.equals(estadoLancamento.codigo) || tipo.equals(estadoLancamento.sigla)) {
        return estadoLancamento;
      }
    }

    throw new NotFoundException(EValidacao.ESTADO_NAO_ENCONTRADO, tipo.toString());
  }
}

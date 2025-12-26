package com.mrsoftware.MRFinanceiro.modelo.enumeradores;

import com.mrsoftware.MRFinanceiro.exception.NotFoundException;
import lombok.Getter;

@Getter
public enum EStatusTitulo {
  EM_ABERTO("EM ABERTO", 0),
  PAGO("PAGO", 1),
  CANCELADO("CANCELADO", 2),
  ATRASADO("ATRASADO", 3),
  BAIXA_PARCIAL("BAIXA PARCIAL", 4),
  BAIXADO("BAIXADO", 5);

  private String descricao;
  private int codigo;

  EStatusTitulo(String descricao, Integer codigo) {
    this.descricao = descricao;
    this.codigo = codigo;
  }

  public static EStatusTitulo obterStatusTitulo(Object entrada) {
    for (EStatusTitulo eStatusTitulo : EStatusTitulo.values()) {
      if (entrada.equals(eStatusTitulo.codigo)) {
        return eStatusTitulo;
      }
    }

    throw new NotFoundException(EValidacao.STATUS_NAO_ENCONTRADO, entrada.toString());
  }
}

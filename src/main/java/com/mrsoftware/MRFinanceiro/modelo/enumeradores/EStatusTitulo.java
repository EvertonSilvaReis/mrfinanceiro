package com.mrsoftware.MRFinanceiro.modelo.enumeradores;

import lombok.Getter;

@Getter
public enum EStatusTitulo {
  EM_ABERTO,
  CANCELADO,
  ATRASADO,
  RECEBIDO,
  PAGO;
}

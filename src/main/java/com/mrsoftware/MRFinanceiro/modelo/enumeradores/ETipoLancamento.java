package com.mrsoftware.MRFinanceiro.modelo.enumeradores;

import com.mrsoftware.MRFinanceiro.exception.NotFoundException;
import java.util.Objects;
import lombok.Getter;

@Getter
public enum ETipoLancamento {
  A_PAGAR("PAGAR", 0),
  RECEBER("RECEBER", 1);

  private String descricao;
  private Integer codigo;

  ETipoLancamento(String descricao, Integer codigo) {
    this.descricao = descricao;
    this.codigo = codigo;
  }

  private ETipoLancamento obterETipoLancamento(Object tipo) {
    if (Objects.isNull(tipo)) return null;

    for (ETipoLancamento tipoLancamento : ETipoLancamento.values()) {
      if (tipo.equals(tipoLancamento.codigo) || tipo.equals(tipoLancamento.descricao)) {
        return tipoLancamento;
      }
    }

    throw new NotFoundException(EValidacao.TIPO_LANCAMENTO_NAO_ENCONTRADO, tipo.toString());
  }
}

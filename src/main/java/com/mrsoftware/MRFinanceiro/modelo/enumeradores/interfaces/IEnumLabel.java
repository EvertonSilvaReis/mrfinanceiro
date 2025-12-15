package com.mrsoftware.MRFinanceiro.modelo.enumeradores.interfaces;

import com.mrsoftware.MRFinanceiro.util.MensagemUtils;

public interface IEnumLabel<E extends Enum<E>> {
  default String getDescricao() {
    return MensagemUtils.getEnumLabel(this);
  }

  default String getDescricao(String[] mensage) {
    return MensagemUtils.getEnumLabel(this, mensage);
  }
}

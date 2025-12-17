package com.mrsoftware.MRFinanceiro.modelo.enumeradores;

import com.mrsoftware.MRFinanceiro.modelo.enumeradores.interfaces.IEnumLabel;
import lombok.Getter;

@Getter
public enum EValidacao implements IEnumLabel<EValidacao> {
  ENTRADA_DADOS_INVALIDA(-1),
  CAMPO_INVALIDO_NAO_IDENTIFICADO(-2),
  TIPO_LANCAMENTO_NAO_ENCONTRADO(-3),
  TIPO_PESSOA_NAO_ENCONTRADO(-4),
  ESTADO_NAO_ENCONTRADO(-5),
  PAIS_NAO_ENCONTRADO(-6),
  UUID_INVALIDO(-7),
  PESSOA_NAO_ENCONTRADA(-8),
  PESSOA_JA_CADASTRADA(-9),
  TIPO_PAGAMENTO_JA_CADASTRADO(-10),
  TIPO_PAGAMENTO_NAO_ENCONTRADO(-11),
  NAO_IDENTIFICADO(-999);

  private Integer codigo;

  EValidacao(Integer codigo) {
    this.codigo = codigo;
  }
}

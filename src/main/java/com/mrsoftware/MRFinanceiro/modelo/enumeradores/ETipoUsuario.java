package com.mrsoftware.MRFinanceiro.modelo.enumeradores;

import com.mrsoftware.MRFinanceiro.exception.NotFoundException;
import java.util.Objects;
import lombok.Getter;

@Getter
public enum ETipoUsuario {
  USUARIO("USER", 0),
  ADMINISTRADOR("ADMIN", 1);

  private String tipo;
  private int codigo;

  ETipoUsuario(String tipo, Integer codigo) {
    this.tipo = tipo;
    this.codigo = codigo;
  }

  public static ETipoUsuario obterTipoUsuario(Object entrada) {
    if (Objects.isNull(entrada)) return null;

    for (ETipoUsuario codigoUsuario : ETipoUsuario.values()) {
      if (entrada.equals(codigoUsuario.codigo) || entrada.equals(codigoUsuario.tipo)) {
        return codigoUsuario;
      }
    }

    throw new NotFoundException(EValidacao.TIPO_PESSOA_NAO_ENCONTRADO, entrada.toString());
  }
}

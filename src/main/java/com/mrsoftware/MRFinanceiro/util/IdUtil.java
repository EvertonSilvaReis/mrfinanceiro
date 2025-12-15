package com.mrsoftware.MRFinanceiro.util;

import com.mrsoftware.MRFinanceiro.exception.BadRequestException;
import com.mrsoftware.MRFinanceiro.modelo.enumeradores.EValidacao;
import java.util.Objects;
import java.util.UUID;

public class IdUtil {

  public static UUID obterUUID(String id) {
    if (Objects.isNull(id)) return null;
    try {
      return UUID.fromString(id);
    } catch (Exception e) {
      throw new BadRequestException(EValidacao.UUID_INVALIDO);
    }
  }
}

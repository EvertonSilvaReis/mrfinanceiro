package com.mrsoftware.MRFinanceiro.dtos.usuario;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UsuarioRetornoDTO {
  private String nome;
  private String codigo;
  private String email;
  private String tipoUsuario;
}

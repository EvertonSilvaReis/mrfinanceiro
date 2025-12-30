package com.mrsoftware.MRFinanceiro.dtos.usuarioPerfil;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UsuarioPerfilRetornoDTO {
  private UUID id;
  private String perfil;
}

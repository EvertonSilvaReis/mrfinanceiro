package com.mrsoftware.MRFinanceiro.dtos.usuario;

import com.mrsoftware.MRFinanceiro.dtos.usuarioPerfil.UsuarioPerfilRetornoDTO;
import java.util.Set;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UsuarioRetornoDTO {
  private UUID id;
  private String nome;
  private String codigo;
  private String email;
  private Set<UsuarioPerfilRetornoDTO> perfis;
}

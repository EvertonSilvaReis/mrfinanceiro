package com.mrsoftware.MRFinanceiro.dtos.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioEntradaDTO {
  @NotBlank private String nome;
  @NotBlank private String email;
  @NotBlank private String senha;
  @NotNull private Integer tipoUsuario;
}

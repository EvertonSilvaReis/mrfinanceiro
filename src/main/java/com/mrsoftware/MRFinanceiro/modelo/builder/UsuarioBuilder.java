package com.mrsoftware.MRFinanceiro.modelo.builder;

import com.mrsoftware.MRFinanceiro.dtos.usuario.UsuarioEntradaDTO;
import com.mrsoftware.MRFinanceiro.dtos.usuario.UsuarioRetornoDTO;
import com.mrsoftware.MRFinanceiro.dtos.usuarioPerfil.UsuarioPerfilRetornoDTO;
import com.mrsoftware.MRFinanceiro.modelo.entidade.Usuario;
import com.mrsoftware.MRFinanceiro.modelo.entidade.UsuarioPerfil;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class UsuarioBuilder {
  private UsuarioEntradaDTO usuarioEntradaDTO;
  private Usuario usuario;
  private UsuarioPerfil usuarioPerfil;
  private UsuarioRetornoDTO usuarioRetornoDTO;

  public UsuarioBuilder addUsuarioEntradaDTO(UsuarioEntradaDTO usuarioEntradaDTO) {
    this.usuarioEntradaDTO = usuarioEntradaDTO;
    return this;
  }

  public UsuarioBuilder addUsuario(Usuario usuario) {
    this.usuario = usuario;
    return this;
  }

  public UsuarioBuilder addUsuarioPerfil(UsuarioPerfil usuarioPerfil) {
    this.usuarioPerfil = usuarioPerfil;
    return this;
  }

  public UsuarioBuilder addUsuarioRetornoDTO(UsuarioRetornoDTO usuarioRetornoDTO) {
    this.usuarioRetornoDTO = usuarioRetornoDTO;
    return this;
  }

  public Usuario buildCadastrarUsuario() {
    return Usuario.builder()
        .nome(usuarioEntradaDTO.getNome())
        .email(usuarioEntradaDTO.getEmail())
        .build();
  }

  public UsuarioRetornoDTO buildEntidadeParaRetorno() {
    return UsuarioRetornoDTO.builder()
        .id(usuario.getId())
        .nome(usuario.getNome())
        .email(usuario.getEmail())
        .perfis(listaUsuarioPerfilRetornoDTO())
        .codigo(usuario.getCodigo())
        .build();
  }

  private Set<UsuarioPerfilRetornoDTO> listaUsuarioPerfilRetornoDTO() {
    if (Objects.nonNull(usuario.getUsuarioPerfis())) {
      return usuario.getUsuarioPerfis().stream()
          .map(
              usuarioPerfil ->
                  UsuarioPerfilRetornoDTO.builder()
                      .id(usuarioPerfil.getId())
                      .perfil(usuarioPerfil.getPerfil())
                      .build())
          .collect(Collectors.toSet());
    }
    return Set.of(
        UsuarioPerfilRetornoDTO.builder()
            .id(usuarioPerfil.getId())
            .perfil(usuarioPerfil.getPerfil())
            .build());
  }

  public Usuario buildDtoRetornoParaEntidade() {
    return Usuario.builder()
        .id(usuarioRetornoDTO.getId())
        .nome(usuarioRetornoDTO.getNome())
        .email(usuarioRetornoDTO.getEmail())
        .codigo(usuarioRetornoDTO.getCodigo())
        .build();
  }
}

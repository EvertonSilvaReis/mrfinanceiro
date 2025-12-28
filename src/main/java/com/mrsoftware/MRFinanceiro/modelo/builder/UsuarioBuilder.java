package com.mrsoftware.MRFinanceiro.modelo.builder;

import com.mrsoftware.MRFinanceiro.dtos.usuario.UsuarioEntradaDTO;
import com.mrsoftware.MRFinanceiro.dtos.usuario.UsuarioRetornoDTO;
import com.mrsoftware.MRFinanceiro.modelo.entidade.Usuario;
import com.mrsoftware.MRFinanceiro.modelo.enumeradores.ETipoUsuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioBuilder {
  private UsuarioEntradaDTO usuarioEntradaDTO;
  private Usuario usuario;

  public UsuarioBuilder addUsuarioEntradaDTO(UsuarioEntradaDTO usuarioEntradaDTO) {
    this.usuarioEntradaDTO = usuarioEntradaDTO;
    return this;
  }

  public UsuarioBuilder addUsuario(Usuario usuario) {
    this.usuario = usuario;
    return this;
  }

  public Usuario buildCadastrarUsuario() {
    return Usuario.builder()
        .nome(usuarioEntradaDTO.getNome())
        .email(usuarioEntradaDTO.getEmail())
        .tipoUsuario(ETipoUsuario.obterTipoUsuario(usuarioEntradaDTO.getTipoUsuario()))
        .build();
  }

  public UsuarioRetornoDTO buildEntidadeParaRetorno() {
    return UsuarioRetornoDTO.builder()
        .nome(usuario.getNome())
        .email(usuario.getEmail())
        .tipoUsuario(usuario.getTipoUsuario().name())
        .codigo(usuario.getCodigo())
        .build();
  }
}

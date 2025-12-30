package com.mrsoftware.MRFinanceiro.modelo.builder;

import com.mrsoftware.MRFinanceiro.dtos.usuario.UsuarioEntradaDTO;
import com.mrsoftware.MRFinanceiro.modelo.entidade.Usuario;
import com.mrsoftware.MRFinanceiro.modelo.entidade.UsuarioPerfil;
import com.mrsoftware.MRFinanceiro.modelo.enumeradores.ETipoUsuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioPerfilBuilder {
  private Usuario usuario;
  private UsuarioEntradaDTO usuarioEntradaDTO;

  public UsuarioPerfilBuilder addUsuario(Usuario usuario) {
    this.usuario = usuario;
    return this;
  }

  public UsuarioPerfilBuilder addUsuarioEntradaDTO(UsuarioEntradaDTO usuarioEntradaDTO) {
    this.usuarioEntradaDTO = usuarioEntradaDTO;
    return this;
  }

  public UsuarioPerfil addUsuarioPerfil() {
    UsuarioPerfil usuarioPerfil = new UsuarioPerfil();
    usuarioPerfil.setUsuario(usuario);
    usuarioPerfil.setPerfil(
        ETipoUsuario.obterTipoUsuario(usuarioEntradaDTO.getTipoUsuario()).name());
    return usuarioPerfil;
  }
}

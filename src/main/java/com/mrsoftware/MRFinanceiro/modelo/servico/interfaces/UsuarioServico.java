package com.mrsoftware.MRFinanceiro.modelo.servico.interfaces;

import com.mrsoftware.MRFinanceiro.dtos.usuario.UsuarioEntradaDTO;
import com.mrsoftware.MRFinanceiro.dtos.usuario.UsuarioRetornoDTO;
import com.mrsoftware.MRFinanceiro.modelo.entidade.Usuario;
import jakarta.validation.constraints.NotNull;
import java.util.Optional;

public interface UsuarioServico {
  UsuarioRetornoDTO cadastrar(UsuarioEntradaDTO usuarioEntradaDTO);

  Optional<Usuario> obterUsuarioPorEmail(String email);

  UsuarioRetornoDTO adicionarPerfilEmUsuario(String id, @NotNull Integer tipoUsuario);
}

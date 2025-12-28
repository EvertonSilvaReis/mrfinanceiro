package com.mrsoftware.MRFinanceiro.modelo.servico.interfaces;

import com.mrsoftware.MRFinanceiro.dtos.usuario.UsuarioEntradaDTO;
import com.mrsoftware.MRFinanceiro.dtos.usuario.UsuarioRetornoDTO;
import com.mrsoftware.MRFinanceiro.modelo.entidade.Usuario;

public interface UsuarioServico {
  UsuarioRetornoDTO cadastrar(UsuarioEntradaDTO usuarioEntradaDTO);

  Usuario obterUsuarioPorEmail(String email);
}

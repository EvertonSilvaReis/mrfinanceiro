package com.mrsoftware.MRFinanceiro.modelo.servico;

import com.mrsoftware.MRFinanceiro.exception.ForbiddenException;
import com.mrsoftware.MRFinanceiro.modelo.entidade.Usuario;
import com.mrsoftware.MRFinanceiro.modelo.enumeradores.EValidacao;
import com.mrsoftware.MRFinanceiro.modelo.servico.interfaces.UsuarioServico;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
  private final UsuarioServico usuarioServico;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Usuario usuario = usuarioServico.obterUsuarioPorEmail(email);

    if (Objects.isNull(usuario)) {
      throw new ForbiddenException(EValidacao.USUARIO_NAO_ENCONTRADO, email);
    }

    return User.builder()
        .username(usuario.getEmail())
        .password(usuario.getSenha())
        .roles(usuario.getTipoUsuario().getTipo())
        .build();
  }
}

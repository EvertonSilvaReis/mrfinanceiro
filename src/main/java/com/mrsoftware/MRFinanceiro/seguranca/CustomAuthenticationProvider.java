package com.mrsoftware.MRFinanceiro.seguranca;

import com.mrsoftware.MRFinanceiro.exception.NotFoundException;
import com.mrsoftware.MRFinanceiro.modelo.entidade.Usuario;
import com.mrsoftware.MRFinanceiro.modelo.enumeradores.EValidacao;
import com.mrsoftware.MRFinanceiro.modelo.servico.interfaces.UsuarioServico;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

  private final UsuarioServico usuarioServico;
  private final PasswordEncoder passwordEncoder;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String email = authentication.getName();
    String senhaEntrada = authentication.getCredentials().toString();

    Optional<Usuario> usuario = usuarioServico.obterUsuarioPorEmail(email);

    if (usuario.isPresent()) {
      boolean senhaUsuarioConfere = passwordEncoder.matches(senhaEntrada, usuario.get().getSenha());

      if (!senhaUsuarioConfere) {
        throw new NotFoundException(EValidacao.USUARIO_NAO_ENCONTRADO, email);
      }
      return new CustomAuthentication(usuario.get());
    } else {
      throw new NotFoundException(EValidacao.USUARIO_NAO_ENCONTRADO, email);
    }
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
  }
}

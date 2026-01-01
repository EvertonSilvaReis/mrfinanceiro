package com.mrsoftware.MRFinanceiro.seguranca;

import com.mrsoftware.MRFinanceiro.modelo.entidade.Usuario;
import com.mrsoftware.MRFinanceiro.modelo.servico.interfaces.UsuarioServico;
import com.mrsoftware.MRFinanceiro.seguranca.jwt.JwtUtils;
import com.mrsoftware.MRFinanceiro.seguranca.servico.UserDetailsImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginSocialSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

  private final UsuarioServico usuarioServico;
  private final JwtUtils jwtUtils;

  @Override
  public void onAuthenticationSuccess(
      HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws ServletException, IOException {

    OAuth2AuthenticationToken oAuth2AuthenticationToken =
        (OAuth2AuthenticationToken) authentication;
    OAuth2User principal = oAuth2AuthenticationToken.getPrincipal();
    String email = principal.getAttribute("email");
    String nome = principal.getAttribute("name");

    Optional<Usuario> usuario = usuarioServico.obterUsuarioPorEmail(email);

    if (usuario.isEmpty()) {
      throw new InternalAuthenticationServiceException("Usuário não cadastrado: " + email);
    } else {
      UserDetailsImpl userDetails = new UserDetailsImpl();
      userDetails.setUsuario(usuario.get());
      userDetails.setEmail(usuario.get().getEmail());
      userDetails.setSenha(usuario.get().getSenha());

      String token = jwtUtils.gerarToken(userDetails);

      String redirectUrl = "http://localhost:8080/rest/token=" + token;

      getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }
  }

  private void contextAuthentication(Usuario usuario) {
    SecurityContextHolder.getContext().setAuthentication(new CustomAuthentication(usuario));
  }
}

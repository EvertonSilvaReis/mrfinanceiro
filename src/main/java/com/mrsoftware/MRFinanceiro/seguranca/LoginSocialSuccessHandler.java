package com.mrsoftware.MRFinanceiro.seguranca;

import com.mrsoftware.MRFinanceiro.dtos.usuario.UsuarioEntradaDTO;
import com.mrsoftware.MRFinanceiro.dtos.usuario.UsuarioRetornoDTO;
import com.mrsoftware.MRFinanceiro.modelo.builder.UsuarioBuilder;
import com.mrsoftware.MRFinanceiro.modelo.entidade.Usuario;
import com.mrsoftware.MRFinanceiro.modelo.enumeradores.ETipoUsuario;
import com.mrsoftware.MRFinanceiro.modelo.servico.interfaces.UsuarioServico;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
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
  private static final String SENHA_PADRAO_CADASTRO_VIA_GOOGLE = "api-mr-financeiro@mr";

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
      UsuarioRetornoDTO usuarioCadastrado =
          usuarioServico.cadastrar(
              new UsuarioEntradaDTO(
                  nome, email, SENHA_PADRAO_CADASTRO_VIA_GOOGLE, ETipoUsuario.USUARIO.getCodigo()));
      contextAuthentication(
          new UsuarioBuilder()
              .addUsuarioRetornoDTO(usuarioCadastrado)
              .buildDtoRetornoParaEntidade());
    } else {
      contextAuthentication(usuario.get());
    }

    super.onAuthenticationSuccess(request, response, authentication);
  }

  private void contextAuthentication(Usuario usuario) {
    SecurityContextHolder.getContext().setAuthentication(new CustomAuthentication(usuario));
  }
}

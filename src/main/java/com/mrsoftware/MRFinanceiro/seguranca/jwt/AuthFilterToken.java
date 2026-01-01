package com.mrsoftware.MRFinanceiro.seguranca.jwt;

import com.mrsoftware.MRFinanceiro.exception.InternalServerErrorException;
import com.mrsoftware.MRFinanceiro.modelo.enumeradores.EValidacao;
import com.mrsoftware.MRFinanceiro.seguranca.servico.UserDetailsImpl;
import com.mrsoftware.MRFinanceiro.seguranca.servico.UserDetailsServicoImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

public class AuthFilterToken extends OncePerRequestFilter {

  @Autowired private JwtUtils jwtUtils;

  @Autowired private UserDetailsServicoImpl usuarioServico;

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    String path = request.getRequestURI();

    return path.startsWith("/oauth2")
        || path.startsWith("/login/oauth2/code")
        || path.startsWith("/login");
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    try {
      String token = getToken(request);
      if (token != null && jwtUtils.validarToken(token)) {
        String email = jwtUtils.getEmailUsuario(token);
        UserDetailsImpl userDetails = (UserDetailsImpl) usuarioServico.loadUserByUsername(email);
        UsernamePasswordAuthenticationToken auth =
            new UsernamePasswordAuthenticationToken(
                usuarioServico, null, userDetails.getAuthorities());
        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(auth);
      }

    } catch (Exception e) {
      throw new InternalServerErrorException(EValidacao.NAO_IDENTIFICADO);
    }
    filterChain.doFilter(request, response);
  }

  private String getToken(HttpServletRequest request) {
    String token = request.getHeader("Authorization");
    if (token != null) {
      return token.replace("Bearer ", "");
    }
    return null;
  }
}

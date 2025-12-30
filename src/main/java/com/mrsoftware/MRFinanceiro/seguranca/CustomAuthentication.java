package com.mrsoftware.MRFinanceiro.seguranca;

import com.mrsoftware.MRFinanceiro.modelo.entidade.Usuario;
import java.util.Collection;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Getter
@RequiredArgsConstructor
public class CustomAuthentication implements Authentication {

  private final Usuario usuario;

  private final String ROLE = "ROLE_";

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.usuario.getUsuarioPerfis().stream()
        .map(roles -> new SimpleGrantedAuthority(ROLE + roles.getPerfil()))
        .collect(Collectors.toList());
  }

  @Override
  public @Nullable Object getCredentials() {
    return null;
  }

  @Override
  public @Nullable Object getDetails() {
    return usuario;
  }

  @Override
  public @Nullable Object getPrincipal() {
    return usuario;
  }

  @Override
  public boolean isAuthenticated() {
    return true;
  }

  @Override
  public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {}

  @Override
  public String getName() {
    return usuario.getEmail();
  }
}

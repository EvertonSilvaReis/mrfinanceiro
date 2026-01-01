package com.mrsoftware.MRFinanceiro.seguranca.servico;

import com.mrsoftware.MRFinanceiro.modelo.entidade.Usuario;
import java.util.Collection;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Setter
@RequiredArgsConstructor
public class UserDetailsImpl implements UserDetails {

  private Usuario usuario;
  private String email;
  private String senha;
  private static final String ROLE = "ROLE_";

  private Collection<? extends GrantedAuthority> authorities;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.usuario.getUsuarioPerfis().stream()
        .map(roles -> new SimpleGrantedAuthority(ROLE + roles.getPerfil()))
        .collect(Collectors.toList());
  }

  @Override
  public String getPassword() {
    return usuario.getSenha();
  }

  @Override
  public String getUsername() {
    return usuario.getEmail();
  }
}

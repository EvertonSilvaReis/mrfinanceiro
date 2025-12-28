package com.mrsoftware.MRFinanceiro.config;

import com.mrsoftware.MRFinanceiro.modelo.servico.CustomUserDetailsService;
import com.mrsoftware.MRFinanceiro.modelo.servico.interfaces.UsuarioServico;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(
    securedEnabled = true,
    jsr250Enabled = true) // Quando as permissões do basic for feita dentro da controller
public class SecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http.csrf(AbstractHttpConfigurer::disable)
        .httpBasic(Customizer.withDefaults())
        .authorizeHttpRequests(
            authorize -> {
              authorize.requestMatchers("/usuario/**").permitAll();
              //              authorize.requestMatchers(HttpMethod.POST,
              // "/usuario/**").hasRole("ADMIN");
              //              authorize.requestMatchers(HttpMethod.GET,
              // "/usuario/**").hasAnyRole("ADMIN", "USER");
              // os requestMatchers foi passado com o PreAuthorize direto no endpoint
              authorize.anyRequest().authenticated();
            })
        .build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(10);
  }

  @Bean
  public UserDetailsService userDetailsService(UsuarioServico usuarioServico) {
    return new CustomUserDetailsService(usuarioServico);
  }
}

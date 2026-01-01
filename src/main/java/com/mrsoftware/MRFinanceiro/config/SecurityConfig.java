package com.mrsoftware.MRFinanceiro.config;

import com.mrsoftware.MRFinanceiro.seguranca.LoginSocialSuccessHandler;
import com.mrsoftware.MRFinanceiro.seguranca.jwt.AuthEntryPointJwt;
import com.mrsoftware.MRFinanceiro.seguranca.jwt.AuthFilterToken;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

  private final AuthEntryPointJwt authEntryPointJwt;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(10);
  }

  @Bean
  public AuthenticationManager authenticationManager(
      AuthenticationConfiguration authenticationConfiguration) {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public AuthFilterToken authFilterToken() {
    return new AuthFilterToken();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(
      HttpSecurity http, LoginSocialSuccessHandler loginSocialSuccessHandler) throws Exception {
    return http.cors(Customizer.withDefaults())
        .csrf(AbstractHttpConfigurer::disable)
        .formLogin(Customizer.withDefaults())
        .exceptionHandling(exception -> exception.authenticationEntryPoint(authEntryPointJwt))
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(
            authorize -> {
              authorize.requestMatchers("/oauth2/**", "/login/oauth2/code/**").permitAll();
              authorize.anyRequest().authenticated();
            })
        .oauth2Login(
            oauth2 -> {
              oauth2.successHandler(loginSocialSuccessHandler);
            })
        .addFilterBefore(authFilterToken(), UsernamePasswordAuthenticationFilter.class)
        .build();
  }
}

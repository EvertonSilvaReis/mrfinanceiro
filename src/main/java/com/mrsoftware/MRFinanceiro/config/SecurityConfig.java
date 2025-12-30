package com.mrsoftware.MRFinanceiro.config;

import com.mrsoftware.MRFinanceiro.seguranca.LoginSocialSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
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
  public SecurityFilterChain securityFilterChain(
      HttpSecurity http, LoginSocialSuccessHandler loginSocialSuccessHandler) throws Exception {
    return http.csrf(AbstractHttpConfigurer::disable)
        .httpBasic(Customizer.withDefaults())
        .formLogin(Customizer.withDefaults())
        .authorizeHttpRequests(
            authorize -> {
              authorize.requestMatchers("/usuario/**").permitAll();
              authorize.anyRequest().authenticated();
            })
        .oauth2Login(
            oauth2 -> {
              oauth2.successHandler(loginSocialSuccessHandler);
            })
        .build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(10);
  }
}

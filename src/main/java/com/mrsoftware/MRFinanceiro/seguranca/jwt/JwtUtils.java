package com.mrsoftware.MRFinanceiro.seguranca.jwt;

import com.mrsoftware.MRFinanceiro.exception.InternalServerErrorException;
import com.mrsoftware.MRFinanceiro.modelo.enumeradores.EValidacao;
import com.mrsoftware.MRFinanceiro.seguranca.servico.UserDetailsImpl;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtils {

  @Value("${projeto.jwtSecret}")
  private String jwtSecret;

  @Value("${projeto.jwtExpirationMs}")
  private int jwtExpirationMs;

  private SecretKey getSignInKey(String jwtSecret) {
    byte[] keyBytes = Base64.getDecoder().decode(jwtSecret);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  public String gerarToken(UserDetailsImpl userDetails) {
    Instant issuedAt = Instant.now();
    Instant expiration = issuedAt.plus(jwtExpirationMs, ChronoUnit.MILLIS);

    return Jwts.builder()
        .subject(userDetails.getUsername())
        .issuedAt(Date.from(issuedAt))
        .expiration(Date.from(expiration))
        .signWith(getSignInKey(jwtSecret))
        .compact();
  }

  public boolean validarToken(String authToken) {
    try {
      Jwts.parser().verifyWith(getSignInKey(jwtSecret)).build().parseSignedClaims(authToken);

      return true;
    } catch (Exception ex) {
      throw new InternalServerErrorException(EValidacao.NAO_IDENTIFICADO);
    }
  }

  public String getEmailUsuario(String token) {
    return Jwts.parser()
        .verifyWith(getSignInKey(jwtSecret))
        .build()
        .parseSignedClaims(token)
        .getPayload()
        .getSubject();
  }
}

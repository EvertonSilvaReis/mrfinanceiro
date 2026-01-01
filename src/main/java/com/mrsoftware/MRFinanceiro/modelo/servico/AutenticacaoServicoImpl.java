package com.mrsoftware.MRFinanceiro.modelo.servico;

import com.mrsoftware.MRFinanceiro.dtos.autenticacao.AutenticacaoEntradaDTO;
import com.mrsoftware.MRFinanceiro.dtos.autenticacao.AutenticacaoRetornoDTO;
import com.mrsoftware.MRFinanceiro.modelo.entidade.Usuario;
import com.mrsoftware.MRFinanceiro.modelo.servico.interfaces.AutenticacaoServico;
import com.mrsoftware.MRFinanceiro.seguranca.CustomAuthenticationProvider;
import com.mrsoftware.MRFinanceiro.seguranca.jwt.JwtUtils;
import com.mrsoftware.MRFinanceiro.seguranca.servico.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AutenticacaoServicoImpl implements AutenticacaoServico {

  private final CustomAuthenticationProvider authenticationManager;
  private final JwtUtils jwtUtils;

  @Override
  public AutenticacaoRetornoDTO login(AutenticacaoEntradaDTO autenticacaoEntradaDTO) {
    try {
      UsernamePasswordAuthenticationToken userAuth =
          new UsernamePasswordAuthenticationToken(
              autenticacaoEntradaDTO.getEmail(), autenticacaoEntradaDTO.getSenha());

      Authentication authenticate = authenticationManager.authenticate(userAuth);

      UserDetailsImpl userDetails = new UserDetailsImpl();
      userDetails.setUsuario((Usuario) authenticate.getPrincipal());

      String token = jwtUtils.gerarToken(userDetails);
      return AutenticacaoRetornoDTO.builder()
          .token(token)
          .usuario(userDetails.getUsername())
          .build();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}

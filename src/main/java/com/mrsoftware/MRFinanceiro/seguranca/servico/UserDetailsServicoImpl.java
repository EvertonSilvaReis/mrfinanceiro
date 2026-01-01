package com.mrsoftware.MRFinanceiro.seguranca.servico;

import com.mrsoftware.MRFinanceiro.exception.ExceptionAbstractImpl;
import com.mrsoftware.MRFinanceiro.exception.InternalServerErrorException;
import com.mrsoftware.MRFinanceiro.exception.NotFoundException;
import com.mrsoftware.MRFinanceiro.modelo.entidade.Usuario;
import com.mrsoftware.MRFinanceiro.modelo.enumeradores.EValidacao;
import com.mrsoftware.MRFinanceiro.modelo.servico.interfaces.UsuarioServico;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServicoImpl implements UserDetailsService {

  private final UsuarioServico usuarioServico;
  private static String MENSAGEM_ERRO = "Ocorreu um erro ao carregar o usuario";

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    try {
      Usuario usuario =
          usuarioServico
              .obterUsuarioPorEmail(username)
              .orElseThrow(
                  () -> new NotFoundException(EValidacao.USUARIO_NAO_ENCONTRADO, username));
      UserDetailsImpl userDetails = new UserDetailsImpl();
      userDetails.setUsuario(usuario);
      userDetails.setEmail(usuario.getEmail());
      userDetails.setSenha(usuario.getSenha());
      return userDetails;
    } catch (ExceptionAbstractImpl e) {
      throw e;
    } catch (Exception ex) {
      log.error(MENSAGEM_ERRO, ex);
      throw new InternalServerErrorException(EValidacao.NAO_IDENTIFICADO);
    }
  }
}

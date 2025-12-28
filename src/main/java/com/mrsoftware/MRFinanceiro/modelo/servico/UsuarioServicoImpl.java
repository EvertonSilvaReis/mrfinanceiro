package com.mrsoftware.MRFinanceiro.modelo.servico;

import com.mrsoftware.MRFinanceiro.dtos.usuario.UsuarioEntradaDTO;
import com.mrsoftware.MRFinanceiro.dtos.usuario.UsuarioRetornoDTO;
import com.mrsoftware.MRFinanceiro.exception.BadRequestException;
import com.mrsoftware.MRFinanceiro.exception.ExceptionAbstractImpl;
import com.mrsoftware.MRFinanceiro.exception.InternalServerErrorException;
import com.mrsoftware.MRFinanceiro.modelo.builder.UsuarioBuilder;
import com.mrsoftware.MRFinanceiro.modelo.entidade.Usuario;
import com.mrsoftware.MRFinanceiro.modelo.enumeradores.EValidacao;
import com.mrsoftware.MRFinanceiro.modelo.repositorios.UsuarioRepositorio;
import com.mrsoftware.MRFinanceiro.modelo.servico.interfaces.ConfiguracaoServico;
import com.mrsoftware.MRFinanceiro.modelo.servico.interfaces.UsuarioServico;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UsuarioServicoImpl implements UsuarioServico {

  @Autowired private UsuarioRepositorio usuarioRepositorio;
  @Autowired private ConfiguracaoServico configuracaoServico;
  @Autowired private PasswordEncoder passwordEncoder;

  private static final String MENSAGEM_ERRO = "Erro ao {} usuário: {}";
  private static final String ULTIMO_CODIGO = "ultimo-codigo-usuario";

  @Transactional
  @Override
  public UsuarioRetornoDTO cadastrar(UsuarioEntradaDTO usuarioEntradaDTO) {
    try {
      validarSeUsuarioJaExiste(usuarioEntradaDTO.getEmail());
      Usuario usuario =
          new UsuarioBuilder().addUsuarioEntradaDTO(usuarioEntradaDTO).buildCadastrarUsuario();
      usuario.setSenha(passwordEncoder.encode(usuarioEntradaDTO.getSenha()));
      adicionaCodigoUsuario(usuario);
      return new UsuarioBuilder()
          .addUsuario(usuarioRepositorio.save(usuario))
          .buildEntidadeParaRetorno();
    } catch (ExceptionAbstractImpl e) {
      throw e;
    } catch (Exception e) {
      log.error(MENSAGEM_ERRO, "usuário", e);
      throw new InternalServerErrorException(EValidacao.NAO_IDENTIFICADO);
    }
  }

  public Usuario obterUsuarioPorEmail(String email) {
    return usuarioRepositorio
        .findByEmail(email)
        .orElseThrow(
            () -> new InternalServerErrorException(EValidacao.USUARIO_NAO_ENCONTRADO, email));
  }

  private void adicionaCodigoUsuario(Usuario usuario) {
    usuario.setCodigo(String.format("%06d", configuracaoServico.obterCodigo(ULTIMO_CODIGO)));
  }

  private void validarSeUsuarioJaExiste(@NotBlank String email) {
    usuarioRepositorio
        .findByEmail(email)
        .ifPresent(
            usuario -> {
              throw new BadRequestException(EValidacao.USUARIO_JA_CADASTRADO, email);
            });
  }
}

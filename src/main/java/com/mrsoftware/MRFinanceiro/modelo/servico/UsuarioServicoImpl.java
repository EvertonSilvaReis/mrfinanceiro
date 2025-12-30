package com.mrsoftware.MRFinanceiro.modelo.servico;

import com.mrsoftware.MRFinanceiro.dtos.usuario.UsuarioEntradaDTO;
import com.mrsoftware.MRFinanceiro.dtos.usuario.UsuarioRetornoDTO;
import com.mrsoftware.MRFinanceiro.exception.BadRequestException;
import com.mrsoftware.MRFinanceiro.exception.ExceptionAbstractImpl;
import com.mrsoftware.MRFinanceiro.exception.InternalServerErrorException;
import com.mrsoftware.MRFinanceiro.modelo.builder.UsuarioBuilder;
import com.mrsoftware.MRFinanceiro.modelo.builder.UsuarioPerfilBuilder;
import com.mrsoftware.MRFinanceiro.modelo.entidade.Usuario;
import com.mrsoftware.MRFinanceiro.modelo.entidade.UsuarioPerfil;
import com.mrsoftware.MRFinanceiro.modelo.enumeradores.EValidacao;
import com.mrsoftware.MRFinanceiro.modelo.repositorios.UsuarioPerfilRepositorio;
import com.mrsoftware.MRFinanceiro.modelo.repositorios.UsuarioRepositorio;
import com.mrsoftware.MRFinanceiro.modelo.servico.interfaces.ConfiguracaoServico;
import com.mrsoftware.MRFinanceiro.modelo.servico.interfaces.UsuarioServico;
import com.mrsoftware.MRFinanceiro.util.IdUtil;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UsuarioServicoImpl implements UsuarioServico {

  @Autowired private UsuarioRepositorio usuarioRepositorio;
  @Autowired private UsuarioPerfilRepositorio usuarioPerfilRepositorio;
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
      usuario = usuarioRepositorio.save(usuario);
      UsuarioPerfil usuarioPerfil =
          usuarioPerfilRepositorio.save(
              new UsuarioPerfilBuilder()
                  .addUsuario(usuario)
                  .addUsuarioEntradaDTO(usuarioEntradaDTO)
                  .addUsuarioPerfil());
      return new UsuarioBuilder()
          .addUsuario(usuario)
          .addUsuarioPerfil(usuarioPerfil)
          .buildEntidadeParaRetorno();
    } catch (ExceptionAbstractImpl e) {
      throw e;
    } catch (Exception e) {
      log.error(MENSAGEM_ERRO, "usuário", e);
      throw new InternalServerErrorException(EValidacao.NAO_IDENTIFICADO);
    }
  }

  @Override
  public Optional<Usuario> obterUsuarioPorEmail(String email) {
    return usuarioRepositorio.findByEmailWithPerfis(email);
  }

  public UsuarioRetornoDTO adicionarPerfilEmUsuario(String idUsuario, Integer perfil) {
    try {
      Usuario usuario = obterUsuarioPorId(IdUtil.obterUUID(idUsuario));
      UsuarioPerfil usuarioPerfil =
          usuarioPerfilRepositorio.save(
              new UsuarioPerfilBuilder()
                  .addUsuario(usuario)
                  .addUsuarioEntradaDTO(UsuarioEntradaDTO.builder().tipoUsuario(perfil).build())
                  .addUsuarioPerfil());
      return new UsuarioBuilder()
          .addUsuario(usuario)
          .addUsuarioPerfil(usuarioPerfil)
          .buildEntidadeParaRetorno();
    } catch (ExceptionAbstractImpl e) {
      throw e;
    } catch (Exception e) {
      log.error(MENSAGEM_ERRO, "adicionar perfil", e);
      throw new InternalServerErrorException(EValidacao.NAO_IDENTIFICADO);
    }
  }

  private void adicionaCodigoUsuario(Usuario usuario) {
    usuario.setCodigo(String.format("%06d", configuracaoServico.obterCodigo(ULTIMO_CODIGO)));
  }

  private void validarSeUsuarioJaExiste(@NotBlank String email) {
    usuarioRepositorio
        .findByEmailWithPerfis(email)
        .ifPresent(
            usuario -> {
              throw new BadRequestException(EValidacao.USUARIO_JA_CADASTRADO, email);
            });
  }

  private Usuario obterUsuarioPorId(UUID id) {
    return usuarioRepositorio
        .findById(id)
        .orElseThrow(
            () -> new BadRequestException(EValidacao.USUARIO_NAO_ENCONTRADO, id.toString()));
  }
}
